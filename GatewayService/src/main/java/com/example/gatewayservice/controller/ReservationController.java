package com.example.gatewayservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Value("${spring.application.relational-service-url}")
    private String relationalServiceUrl;

    private final RestTemplate restTemplate;

    public ReservationController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createReservation(
            @RequestParam Long roomId,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam int guestCount) {

        String url = relationalServiceUrl + "/reservations/create";

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("roomId", roomId)
                .queryParam("startDate", startDate)
                .queryParam("endDate", endDate)
                .queryParam("guestCount", guestCount);

        ResponseEntity<?> response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST, HttpEntity.EMPTY, Object.class);

        return response;
    }

}

