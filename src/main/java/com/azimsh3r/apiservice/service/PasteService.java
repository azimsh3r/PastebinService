package com.azimsh3r.apiservice.service;

import com.azimsh3r.apiservice.dto.PasteRequestDTO;
import com.azimsh3r.apiservice.model.Metadata;
import com.azimsh3r.apiservice.repository.MetadataRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PasteService {
    private final HashService hashService;

    private final S3Service s3Service;

    private final RedisTemplate<String, String> redisTemplate;

    private final MetadataRepository metadataRepository;

    @Autowired
    public PasteService(HashService hashService, S3Service s3Service, RedisTemplate<String, String> redisTemplate, MetadataRepository metadataRepository) {
        this.hashService = hashService;
        this.s3Service = s3Service;
        this.redisTemplate = redisTemplate;
        this.metadataRepository = metadataRepository;
    }

    public String createPaste(PasteRequestDTO pasteRequestDTO) {
        String text = pasteRequestDTO.getText();

        String hash = hashService.generateHash(text);
        boolean isStoredInS3 = false;

        if (text.length() > 1024) {
            s3Service.uploadToS3(hash, text);
            isStoredInS3 = true;
        } else {
            cachePaste(hash, text);
        }

        Metadata metadata = new Metadata();
        metadata.setHash(hash);
        metadata.setTextLength(text.length());
        metadata.setIsStoredInS3(isStoredInS3);
        metadata.setExpiresAt(
                LocalDateTime.now().plusMinutes(pasteRequestDTO.getExpiresInMin())
        );
        metadataRepository.save(metadata);

        return hash;
    }

    private void cachePaste(String hash, String text) {
        redisTemplate.opsForValue().set(hash, text);
    }

    @HystrixCommand(
            fallbackMethod = "returnDefaultText",
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="3000"),
                    @HystrixProperty (name="circuitBreaker.requestVolumeThreshold", value="10"),
                    @HystrixProperty (name="circuitBreaker.errorThresholdPercentage", value="75"),
                    @HystrixProperty (name="circuitBreaker.sleepWindowInMilliseconds", value="7000"),
                    @HystrixProperty (name="metrics.rollingStats.timeInMilliseconds", value="15000"),
            },
            threadPoolKey = "allHashThreadPool",
            threadPoolProperties = {
                    @HystrixProperty(
                            name = "coreSize",
                            value = "30"
                    ),
                    @HystrixProperty (
                            name="maxQueueSize",
                            value = "10"
                    )
            }
    )
    private String getPasteFromCache(String hash) {
        return redisTemplate.opsForValue().get(hash);
    }

    public String getPaste(String hash) {
        String text = redisTemplate.opsForValue().get(hash);
        if (text != null) {
            return text;
        }

        Optional<Metadata> metadata = metadataRepository.findByHash(hash);
        if (metadata.isPresent()) {
            if (metadata.get().getIsStoredInS3()) {
                text = s3Service.getPasteFromS3(hash);
            } else {
                text = getPasteFromCache(hash);
            }
        }
        return text;
    }

    public String returnDefaultText() {
        return "Sorry, text is not available!";
    }
}
