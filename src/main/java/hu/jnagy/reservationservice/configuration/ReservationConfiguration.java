package hu.jnagy.reservationservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ReservationConfiguration {
    @Value("${user.service.url}")
    private String userServiceURL = "http://localhost:8081/user/{userId}";
    @Value("${room.service.url}")
    private String roomServiceURL = "http://localhost:8082/rooms";

    public String getUserServiceURL() {
        return userServiceURL;
    }

    public String getRoomServiceURL() {
        return roomServiceURL;
    }
}
