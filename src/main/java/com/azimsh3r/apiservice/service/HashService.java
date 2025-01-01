package com.azimsh3r.apiservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class HashService {
    private final RestTemplate restTemplate;

    @Autowired
    public HashService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateHash(String text) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("text", text);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody);

        return restTemplate.exchange(
                "http://hashservice/hash",
                HttpMethod.POST,
                entity,
                String.class
        ).getBody();
    }
}
