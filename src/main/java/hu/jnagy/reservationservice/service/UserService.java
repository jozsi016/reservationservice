package hu.jnagy.reservationservice.service;

import hu.jnagy.reservationservice.api.UserServiceResponse;
import hu.jnagy.reservationservice.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    private RestTemplate restTemplate;
    private String userServiceURL = "http://localhost:8081/user/{userId}";

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public User getUserById(long userId) {
        ResponseEntity<UserServiceResponse> usersServiceResponse = restTemplate.getForEntity(userServiceURL, UserServiceResponse.class);
        return usersServiceResponse.getBody().getUser();
    }
}
