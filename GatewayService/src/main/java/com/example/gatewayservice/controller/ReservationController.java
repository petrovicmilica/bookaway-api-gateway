package com.example.gatewayservice.controller;

import com.example.gatewayservice.dto.ReservationDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationDto>> getReservationsByUserId(@PathVariable Long userId) {
        String url = relationalServiceUrl + "/reservations/user/" + userId;

        ResponseEntity<ReservationDto[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                ReservationDto[].class
        );

        List<ReservationDto> reservations = List.of(response.getBody());
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public Object getById(@PathVariable Long id) {
        String url = relationalServiceUrl + "/reservations/" + id;
        return restTemplate.getForObject(url, Object.class);
    }

}

