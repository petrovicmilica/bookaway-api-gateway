package com.example.gatewayservice.controller;

import com.example.gatewayservice.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.application.relational-service-url}")
    private String relationalServiceUrl;

    @GetMapping("/find/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        String url = relationalServiceUrl + "/users/find/" + username;
        ResponseEntity<UserDto> response = restTemplate.getForEntity(url, UserDto.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        String url = relationalServiceUrl + "/users/find/" + id;
        ResponseEntity<UserDto> response = restTemplate.getForEntity(url, UserDto.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
