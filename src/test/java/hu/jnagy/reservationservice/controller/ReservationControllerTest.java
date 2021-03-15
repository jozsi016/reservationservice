package hu.jnagy.reservationservice.controller;

import hu.jnagy.reservationservice.model.Reservation;
import hu.jnagy.reservationservice.model.Room;
import hu.jnagy.reservationservice.model.User;
import hu.jnagy.reservationservice.responsetype.ReservationResponse;
import hu.jnagy.reservationservice.service.ReservationService;
import hu.jnagy.reservationservice.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @MockBean
    private ReservationService reservationServiceMock;
    @MockBean
    private UserService userService;

    @Test
    public void shouldCreateReservation() {
        //given
        User user = new User(1L, "Tom");
        Room room = new Room(1L, 5000);
        LocalDate start = LocalDate.now().minusDays(5L);
        LocalDate end = LocalDate.now();
        Room room2 = new Room(2L, 5000);
        LocalDate startFuture = LocalDate.now().plusDays(3);
        LocalDate endFuture = LocalDate.now().plusDays(8);
        reservationServiceMock.createReservation(user, room, start, end);
        reservationServiceMock.createReservation(user, room2, startFuture, endFuture);
        Reservation expected = new Reservation(
                1, 1, 1, LocalDate.of(2021, 2, 15), LocalDate.of(2021, 2, 20), 25000);
        when(reservationServiceMock.getReservationById(anyLong())).thenReturn(expected);

        //when
        ResponseEntity<ReservationResponse> response =
                this.restTemplate.getForEntity("/reservation/1", ReservationResponse.class);

        //then
        Reservation actual = response.getBody().getReservation();
        verify(reservationServiceMock).getReservationById(anyLong());
    }
}
