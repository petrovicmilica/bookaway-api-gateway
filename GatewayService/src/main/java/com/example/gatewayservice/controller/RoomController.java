package com.example.gatewayservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    @Value("${spring.application.relational-service-url}")
    private String relationalServiceUrl;

    private final RestTemplate restTemplate;

    public RoomController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchAvailableRooms(
            @RequestParam int guestCount,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam Long hotelId) {

        String url = String.format("%s/rooms/search?guestCount=%d&startDate=%s&endDate=%s&hotelId=%d",
                relationalServiceUrl, guestCount, startDate, endDate, hotelId);

        ResponseEntity<?> response = restTemplate.getForEntity(url, List.class);

        return response;
    }
}
