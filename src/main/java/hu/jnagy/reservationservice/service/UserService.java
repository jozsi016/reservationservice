package hu.jnagy.reservationservice.service;

import hu.jnagy.reservationservice.api.userservice.ApiUser;
import hu.jnagy.reservationservice.api.userservice.UserServiceResponse;
import hu.jnagy.reservationservice.configuration.ReservationConfiguration;
import hu.jnagy.reservationservice.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    private RestTemplate restTemplate;
    private ReservationConfiguration configuration;
    private String userServiceURL;

    public UserService(RestTemplate restTemplate, ReservationConfiguration configuration) {
        this.restTemplate = restTemplate;
        this.configuration = configuration;
        this.userServiceURL =  configuration.getUserServiceURL();
    }

    public ApiUser getUserById(long userId) {
        try {
        ResponseEntity<UserServiceResponse> usersServiceResponse = restTemplate.getForEntity(userServiceURL+"/" +userId, UserServiceResponse.class);
        return usersServiceResponse.getBody().getUser();
        } catch(HttpClientErrorException hcee){
            throw new UserNotFoundException("", hcee);
        }
    }
}
