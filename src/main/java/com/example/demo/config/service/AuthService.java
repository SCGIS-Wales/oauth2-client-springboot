package com.example.demo.service;

import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Value("${OAUTH2_CLIENT_ID}")
    private String clientId;

    @Value("${OAUTH2_API_KEY}")
    private String apiKey;

    @Value("${OAUTH2_USERNAME}")
    private String username;

    @Value("${OAUTH2_PASSWORD}")
    private String password;

    @Value("${OAUTH2_URL}")
    private String url;

    @Value("${OAUTH_RETRY_INTERVAL}")
    private long retryInterval;

    private final RestTemplate restTemplate;
    private final MeterRegistry meterRegistry;

    public AuthService(RestTemplate restTemplate, MeterRegistry meterRegistry) {
        this.restTemplate = restTemplate;
        this.meterRegistry = meterRegistry;
    }

    @Scheduled(fixedDelayString = "${OAUTH_RETRY_INTERVAL}000")
    public void getToken() {
        checkEnvVariables();

        String auth = Base64.getEncoder().encodeToString((clientId + ":" + apiKey).getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + auth);

        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("username", username);
        params.put("password", password);
        params.put("client_id", clientId);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            meterRegistry.counter("response.status.code", "status", String.valueOf(response.getStatusCodeValue())).increment();
            logger.info("Successfully fetched OAuth2 token. Status code: " + response.getStatusCodeValue());
        } catch (RestClientException e) {
            logger.error("Error occurred: {}", e.getMessage());
        }
    }

    private void checkEnvVariables() {
        if (clientId == null || apiKey == null || username == null || password == null || url == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Environment variables OAUTH2_CLIENT_ID, OAUTH2_API_KEY, OAUTH2_USERNAME, OAUTH2_PASSWORD, and OAUTH2_URL must be set");
        }
    }
}
