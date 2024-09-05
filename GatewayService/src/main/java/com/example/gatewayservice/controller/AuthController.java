package com.example.gatewayservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.application.relational-service-url}")
    private String relationalServiceUrl;

    @GetMapping("/tryLogin")
    public String loginStudent() {
        try {
            return "uspesna autentifikacija!";
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> loginData) {
        String url = relationalServiceUrl + "/auth/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(loginData, headers);

        try {
            ResponseEntity<Map<String, String>> relationalResponse = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Map<String, String>>() {}
            );

            return ResponseEntity.status(relationalResponse.getStatusCode()).body(relationalResponse.getBody());

        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(Collections.singletonMap("error", e.getResponseBodyAsString()));
        } catch (HttpServerErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(Collections.singletonMap("error", e.getResponseBodyAsString()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("error", "Login failed in relational database: " + e.getMessage()));
        }
    }


    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Map<String, Object> userData) {
        Map<String, String> response = new HashMap<>();
        ResponseEntity<Map> relationalResponse;
        try {
            String url = relationalServiceUrl + "/auth/register";
            relationalResponse = restTemplate.postForEntity(url, userData, Map.class);
        } catch (Exception e) {
            response.put("error", "Registration failed in relational database: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }

        if (relationalResponse.getStatusCode().is2xxSuccessful()) {
            Map<String, String> relationalBody = relationalResponse.getBody();
            if (relationalBody != null && "User registered successfully".equals(relationalBody.get("message"))) {
                response.put("message", "User registered successfully.");
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "Unexpected response from relational service.");
                return ResponseEntity.status(500).body(response);
            }
        } else {
            response.put("error", "Registration failed with status: " + relationalResponse.getStatusCode());
            return ResponseEntity.status(relationalResponse.getStatusCode()).body(response);
        }
    }

}
