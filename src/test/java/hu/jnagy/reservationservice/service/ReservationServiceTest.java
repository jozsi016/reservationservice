package hu.jnagy.reservationservice.service;

import hu.jnagy.reservationservice.model.Reservation;
import hu.jnagy.reservationservice.model.Room;
import hu.jnagy.reservationservice.model.User;
import hu.jnagy.reservationservice.repository.ReservationRepository;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;

public class ReservationServiceTest {
    private ReservationService reservationService;
    private UserService userService;
    private ReservationRepository reservationRepository;

    @Before
    public void setUp() {
        reservationRepository = new ReservationRepository();
        reservationService = new ReservationService(reservationRepository,null,null);
    }

    @Test
    public void shouldCreateReservation() {
        //Given
        Room room = new Room(1L, 5000);
        User user = new User(1L, "Tom");
        LocalDate start = LocalDate.now().minusDays(5L);
        LocalDate end = LocalDate.now();
        reservationService.createReservation(user, room, start, end);
        //When
        long actualRoomId = reservationService.getReservationById(1L).getRoomId();
        //Then
        assertThat(1L, is(actualRoomId));
    }

    @Test
    public void shouldGetUserReservationByUserId() {
        //Given
        User user = new User(1L, "Tom");
        Room room = new Room(1L, 5000);
        LocalDate start = LocalDate.now().minusDays(5L);
        LocalDate end = LocalDate.now();
        reservationService.createReservation(user, room, start, end);
        Room room2 = new Room(2L, 5000);
        LocalDate startFuture = LocalDate.now().plusDays(3);
        LocalDate endFuture = LocalDate.now().plusDays(8);
        reservationService.createReservation(user, room2, startFuture, endFuture);

        //When
        long actualRoomId = reservationService.getAllUserReservationsByUserId(1L).get(1).getRoomId();

        //Then
        assertThat(2L, is(actualRoomId));
    }

    @Test
    public void shouldFilteredReservationByStartDate() {
        //Given
        User user = new User(1L, "Tom");
        LocalDate startDateFilter = LocalDate.now().minusYears(1);
        Room room = new Room(1L, 5000);
        LocalDate start = LocalDate.now().minusDays(5L);
        LocalDate end = LocalDate.now();
        Room room2 = new Room(2L, 5000);
        LocalDate startFuture = LocalDate.now().plusDays(3);
        LocalDate endFuture = LocalDate.now().plusDays(8);
        Predicate<Reservation> filterForStartDate =
                (r) -> r.getUserId() == user.getId() && r.getStartDate().compareTo(startDateFilter) >= 0;
        reservationService.createReservation(user, room, start, end);
        reservationService.createReservation(user, room2, startFuture, endFuture);

        //When
        List<Reservation> actual = reservationService.getFilteredReservation(filterForStartDate);

        //Then
        assertThat(1L, is(actual.get(0).getRoomId()));
    }
}
