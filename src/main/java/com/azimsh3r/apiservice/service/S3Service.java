package com.azimsh3r.apiservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.InputStream;

@Service
public class S3Service {

    private final AmazonS3 s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3Service() {
        this.s3Client = AmazonS3ClientBuilder.standard().build();
    }

    public void uploadToS3(String hash, String text) {
        InputStream inputStream = new java.io.ByteArrayInputStream(text.getBytes());
        PutObjectRequest request = new PutObjectRequest(bucketName, hash, inputStream, null);
        s3Client.putObject(request);
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
    public String getPasteFromS3(String hash) {
        S3Object s3Object = s3Client.getObject(bucketName, hash);
        try (InputStream inputStream = s3Object.getObjectContent()) {
            return new String(inputStream.readAllBytes());
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve paste from S3", e);
        }
    }

    public String returnDefaultText() {
        return "Sorry, text is not available!";
    }
}
