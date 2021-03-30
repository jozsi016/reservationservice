package hu.jnagy.reservationservice.service;

import hu.jnagy.reservationservice.api.roomservice.RoomServiceResponse;
import hu.jnagy.reservationservice.configuration.ReservationConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RoomService {
    private RestTemplate restTemplate;
    private ReservationConfiguration configuration;
    private String roomServiceURL;

    public RoomService(RestTemplate restTemplate, ReservationConfiguration configuration) {
        this.restTemplate = restTemplate;
        this.configuration = configuration;
        this.roomServiceURL = configuration.getRoomServiceURL();
    }

    public ResponseEntity<RoomServiceResponse> getRooms() {
        return restTemplate.getForEntity(roomServiceURL, RoomServiceResponse.class);
    }
}
