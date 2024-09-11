package com.example.gatewayservice.controller;

import com.example.gatewayservice.dto.ChangePasswordDto;
import com.example.gatewayservice.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.application.relational-service-url}")
    private String relationalServiceUrl;

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        String url = relationalServiceUrl + "/users/" + id;
        ResponseEntity<UserDto> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(userDto),
                UserDto.class
        );

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        String url = relationalServiceUrl + "/users/find/" + username;
        ResponseEntity<UserDto> response = restTemplate.getForEntity(url, UserDto.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        String url = relationalServiceUrl + "/users/findById/" + id;
        ResponseEntity<UserDto> response = restTemplate.getForEntity(url, UserDto.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @PostMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        String url = relationalServiceUrl + "/auth/change-password";

        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(changePasswordDto),
                new ParameterizedTypeReference<Map<String, String>>() {}
        );

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
