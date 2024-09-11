package com.example.gatewayservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Value("${spring.application.document-service-url}")
    private String documentServiceUrl;

    private final RestTemplate restTemplate;

    public ReviewController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public ResponseEntity<Object> createReview(@RequestBody Object review) {
        String createReviewUrl = documentServiceUrl + "/reviews";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> requestEntity = new HttpEntity<>(review, headers);

        ResponseEntity<Object> responseEntity = restTemplate.exchange(
                createReviewUrl,
                HttpMethod.POST,
                requestEntity,
                Object.class
        );

        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<Object> getReviewsByHotelId(@PathVariable("hotelId") String hotelId) {
        String getReviewsUrl = documentServiceUrl + "/reviews/hotel/" + hotelId;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(
                getReviewsUrl,
                HttpMethod.GET,
                requestEntity,
                Object.class
        );

        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    @PutMapping("/{reviewId}/{status}")
    public ResponseEntity<Object> updateReviewStatus(@PathVariable String reviewId, @PathVariable String status) {
        String updateReviewUrl = documentServiceUrl + "/reviews/" + reviewId + "/" + status;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Object> responseEntity = restTemplate.exchange(
                updateReviewUrl,
                HttpMethod.PUT,
                requestEntity,
                Object.class
        );

        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

}
