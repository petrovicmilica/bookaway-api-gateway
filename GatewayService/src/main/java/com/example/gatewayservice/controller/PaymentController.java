package com.example.gatewayservice.controller;

import com.example.gatewayservice.dto.PaymentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Value("${spring.application.relational-service-url}")
    private String relationalServiceUrl;

    private final RestTemplate restTemplate;

    public PaymentController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/{reservationId}")
    public ResponseEntity<PaymentDto> createPayment(@RequestBody PaymentDto paymentDto, @PathVariable String reservationId) {
        String createPaymentUrl = relationalServiceUrl + "/payments/" + reservationId;
        HttpEntity<PaymentDto> request = new HttpEntity<>(paymentDto);

        ResponseEntity<PaymentDto> response = restTemplate.exchange(
                createPaymentUrl,
                HttpMethod.POST,
                request,
                PaymentDto.class
        );

        return ResponseEntity.ok(response.getBody());
    }
}
