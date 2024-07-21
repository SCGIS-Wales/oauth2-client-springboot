package com.example.demo.controller;

import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

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

    private final RestTemplate restTemplate;
    private final MeterRegistry meterRegistry;

    public AuthController(RestTemplate restTemplate, MeterRegistry meterRegistry) {
        this.restTemplate = restTemplate;
        this.meterRegistry = meterRegistry;
    }

    @GetMapping("/token")
    public ResponseEntity<String> getToken() {
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
            return response;
        } catch (RestClientException e) {
            logger.error("Error occurred: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }

    private void checkEnvVariables() {
        if (clientId == null || apiKey == null || username == null || password == null || url == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Environment variables OAUTH2_CLIENT_ID, OAUTH2_API_KEY, OAUTH2_USERNAME, OAUTH2_PASSWORD, and OAUTH2_URL must be set");
        }
    }
}
