package hu.jnagy.reservationservice.controller;

import hu.jnagy.reservationservice.model.Reservation;
import hu.jnagy.reservationservice.model.Room;
import hu.jnagy.reservationservice.model.User;
import hu.jnagy.reservationservice.requestobjcet.CreateReservationRequest;
import hu.jnagy.reservationservice.responsetype.ReservationResponse;
import hu.jnagy.reservationservice.service.ReservationService;
import hu.jnagy.reservationservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@RestController
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;

    public ReservationController(ReservationService reservationService, UserService userService) {
        this.reservationService = reservationService;
        this.userService = userService;
    }

    @PostMapping("/reservation")
    public void createReservation(CreateReservationRequest createReservationRequest) {
        LocalDate start = reservationService.getLocalDate(createReservationRequest.getStartStr());
        LocalDate end = reservationService.getLocalDate(createReservationRequest.getEndStr());
        User user = userService.getUserById(createReservationRequest.getUserId());
        List<Room> rooms = reservationService.listOfAvailableRooms(start, end);
        Optional<Room> room = rooms.stream().filter(r -> createReservationRequest.getRoomId() == r.getId()).findFirst();
        if (room.isPresent()) {
            reservationService.createReservation(user, room.get(), start, end);
        }
    }

    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable long reservationId) {
        Reservation reservationById = reservationService.getReservationById(reservationId);
        ReservationResponse response = new ReservationResponse.Builder().withReservation(reservationById).build();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/reservation/availablerooms")
    public List<Room> listOfAvailableRooms(@RequestParam String startStr, String endStr) {
        LocalDate start = reservationService.getLocalDate(startStr);
        LocalDate end = reservationService.getLocalDate(endStr);
        return reservationService.listOfAvailableRooms(start, end);
    }

    @DeleteMapping("/reservation/{reservationId}")
    public void deleteReservation(@PathVariable long reservationId) {
        reservationService.deleteReservation(reservationId);
    }

    @GetMapping("/reservation/user/{userId}")
    public List<Reservation> getAllUserReservationsByUserId(@RequestParam long userId) {
        return reservationService.getAllUserReservationsByUserId(userId);
    }

    public List<Reservation> getFilteredReservation(Predicate<Reservation> predicate) {
        return reservationService.getFilteredReservation(predicate);
    }

}
