package com.example.gatewayservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {
    @Value("${spring.application.relational-service-url}")
    private String relationalServiceUrl;

    private final RestTemplate restTemplate;

    public HotelController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public Object getAllHotels() {
        String url = relationalServiceUrl + "/hotels";
        return restTemplate.getForObject(url, Object.class);
    }

    @GetMapping("/{id}/rooms")
    public Object getRoomsByHotelId(@PathVariable Long id) {
        String url = relationalServiceUrl + "/hotels/" + id + "/rooms";
        return restTemplate.getForObject(url, Object.class);
    }

    @GetMapping("/{id}")
    public Object getHotelById(@PathVariable Long id) {
        String url = relationalServiceUrl + "/hotels/" + id;
        return restTemplate.getForObject(url, Object.class);
    }
}
