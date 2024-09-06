package com.example.gatewayservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

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

    @GetMapping("/reservations/{id}")
    public Object getHotelByReservationId(@PathVariable Long id) {
        String url = relationalServiceUrl + "/hotels/reservations/" + id;
        return restTemplate.getForObject(url, Object.class);
    }


    @GetMapping("/search")
    public Object searchHotels(
            @RequestParam(required = false) String address,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false, defaultValue = "0") int numberOfGuests) {
        String url = relationalServiceUrl + "/hotels/search?address=" + (address != null ? address : "") +
                "&startDate=" + (startDate != null ? startDate.toString() : "") +
                "&endDate=" + (endDate != null ? endDate.toString() : "") +
                "&numberOfGuests=" + numberOfGuests;
        return restTemplate.getForObject(url, Object.class);
    }
}
