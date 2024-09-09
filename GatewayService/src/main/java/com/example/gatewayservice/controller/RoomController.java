package com.example.gatewayservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    @Value("${spring.application.relational-service-url}")
    private String relationalServiceUrl;

    @Value("${spring.application.document-service-url}")
    private String documentServiceUrl;

    private final RestTemplate restTemplate;

    public RoomController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{id}")
    public Object getRoomById(@PathVariable Long id) {
        String url = relationalServiceUrl + "/rooms/" + id;
        return restTemplate.getForObject(url, Object.class);
    }

    @GetMapping("/facilities/{id}")
    public Object getRoomFacilitiesByRoomId(@PathVariable Long id) {
        String url = documentServiceUrl + "/room-facilities/room/" + id;
        return restTemplate.getForObject(url, Object.class);
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
