package hu.jnagy.reservationservice.controller;

import hu.jnagy.reservationservice.model.Reservation;
import hu.jnagy.reservationservice.api.CreateReservationRequest;
import hu.jnagy.reservationservice.api.ReservationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 9090)
public class ReservationControllerITest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldCreateReservation() {
        //given
        Reservation expected = new Reservation(
                1, 1, 1, LocalDate.of(2021, 4, 10), LocalDate.of(2021, 4, 15), 25000);

        CreateReservationRequest request = new CreateReservationRequest.Builder().withStartStr("2021-04-10").withEndStr("2021-04-15")
                .withUserId(1).withRoomId(1).build();
        //when
        ResponseEntity<ReservationResponse> responseEntity = restTemplate.postForEntity("/reservation", request, ReservationResponse.class);
        //then
        ReservationResponse actual = responseEntity.getBody();
        assertThat(actual.getReservation(), is(expected));
    }

    @Test
    public void shouldReturn404WhenUserNotFound() {
        //given
        CreateReservationRequest request = new CreateReservationRequest.Builder().withStartStr("2021-04-10").withEndStr("2021-04-15")
                .withUserId(123).withRoomId(1).build();
        //when
        ResponseEntity<ReservationResponse> responseEntity = restTemplate.postForEntity("/reservation", request, ReservationResponse.class);
        //then
        System.err.println(responseEntity.getStatusCode());
        HttpStatus actual = responseEntity.getStatusCode();
        assertThat(actual, is(HttpStatus.NOT_FOUND));
    }


    @Test
    public void shouldReturn404WhenRoomNotFound() {
        //given
        CreateReservationRequest request = new CreateReservationRequest.Builder().withStartStr("2021-04-10").withEndStr("2021-04-15")
                .withUserId(1).withRoomId(35).build();
        //when
        ResponseEntity<ReservationResponse> responseEntity = restTemplate.postForEntity("/reservation", request, ReservationResponse.class);
        //then
        HttpStatus actual = responseEntity.getStatusCode();
        assertThat(actual, is(HttpStatus.NOT_FOUND));
    }

    //@Test
    public void shouldReturn409WhenRoomOccupiedInGivenTimePeriod() {
        //given
        CreateReservationRequest request = new CreateReservationRequest.Builder().withStartStr("2021-04-10").withEndStr("2021-04-15")
                .withUserId(1).withRoomId(2).build();
        //when
        ResponseEntity<ReservationResponse> responseEntity = restTemplate.postForEntity("/reservation", request, ReservationResponse.class);

        //then
        HttpStatus actual = responseEntity.getStatusCode();
        assertThat(actual, is(HttpStatus.CONFLICT));
    }
}
