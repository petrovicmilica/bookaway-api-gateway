package com.example.gatewayservice.config;


import com.example.gatewayservice.dto.UserGatewayDto;
import com.example.gatewayservice.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final RestTemplate restTemplate;

    @Value("${spring.application.relational-service-url}")
    private String relationalServiceUrl;

    public CustomUserDetailsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("USAO U CUSTOM USER DETAILS SERVICE");
        System.out.println("username::: " + username);

        try {
            ResponseEntity<UserGatewayDto> response = restTemplate.exchange(
                    relationalServiceUrl + "/users/" + username,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    });

            System.out.println("Status code: " + response.getStatusCode());

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                UserGatewayDto userDto = response.getBody();
                User user = new User(userDto.getUsername(), userDto.getPassword(), userDto.getRole());
                System.out.println("Pronasao je usera: " + user.getUsername());
                return new CustomUserDetails(user);
            } else {
                System.out.println("Nije pronasao usera sa prosledjenim usernameom! Status code: " + response.getStatusCode());
                throw new UsernameNotFoundException("User not found.");
            }
        } catch (Exception e) {
            System.out.println("Greska prilikom poziva relacionog servisa: " + e.getMessage());
            throw new UsernameNotFoundException("User not found.");
        }
    }

}