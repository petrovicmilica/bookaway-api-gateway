package com.example.gatewayservice.controller;

import com.example.gatewayservice.model.ReservationPaymentDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    @Value("${spring.application.document-service-url}")
    private String documentServiceUrl;

    private final RestTemplate restTemplate;

    public DocumentController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public ResponseEntity<ReservationPaymentDocument> createReservationPaymentDocument(@RequestBody ReservationPaymentDocument document) {
        String url = documentServiceUrl + "/documents";
        HttpEntity<ReservationPaymentDocument> request = new HttpEntity<>(document);

        ResponseEntity<ReservationPaymentDocument> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                ReservationPaymentDocument.class
        );

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

   /* @PostMapping("/sendDocument")
    public ResponseEntity<String> sendDocument(@RequestParam String email, @RequestParam String documentId) {
        String url = documentServiceUrl + "/documents/sendDocument";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("documentId", documentId);

        return restTemplate.postForEntity(url, requestBody, String.class);
    }*/

    @Async
    @PostMapping("/sendDocument")
    public CompletableFuture<ResponseEntity<String>> sendDocument(@RequestParam String email, @RequestParam String documentId) {
        return CompletableFuture.supplyAsync(() -> {
            String url = documentServiceUrl + "/documents/sendDocument";
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("email", email);
            requestBody.put("documentId", documentId);

            ResponseEntity<String> response = restTemplate.postForEntity(url, requestBody, String.class);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        });
    }

}
