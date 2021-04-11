package hu.jnagy.reservationservice.controller;

import hu.jnagy.reservationservice.api.ErrorResponse;
import hu.jnagy.reservationservice.api.userservice.ApiUser;
import hu.jnagy.reservationservice.api.ApiConverter;
import hu.jnagy.reservationservice.exception.ResourceNotFoundException;
import hu.jnagy.reservationservice.exception.UserNotFoundException;
import hu.jnagy.reservationservice.model.Reservation;
import hu.jnagy.reservationservice.model.Room;
import hu.jnagy.reservationservice.model.User;
import hu.jnagy.reservationservice.api.CreateReservationRequest;
import hu.jnagy.reservationservice.api.ReservationResponse;
import hu.jnagy.reservationservice.service.ReservationService;
import hu.jnagy.reservationservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

@RestController
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;
    private final ApiConverter apiConverter;

    public ReservationController(ReservationService reservationService, UserService userService) {
        this.reservationService = reservationService;
        this.userService = userService;
        this.apiConverter = new ApiConverter();
    }

    @PostMapping("/reservation")
    public ReservationResponse createReservation(@RequestBody CreateReservationRequest createReservationRequest) {
        LocalDate start = reservationService.getLocalDate(createReservationRequest.getStartStr());
        LocalDate end = reservationService.getLocalDate(createReservationRequest.getEndStr());
        ApiUser apiUser = userService.getUserById(createReservationRequest.getUserId());
        User user = apiConverter.convertToUser(apiUser);
        List<Room> rooms = reservationService.listOfAvailableRooms(start, end);
        Optional<Room> room = rooms.stream().filter(r -> createReservationRequest.getRoomId() == r.getId()).findFirst();
        Reservation reservation = reservationService.createReservation(user, room.orElseThrow(), start, end);
        ReservationResponse response = new ReservationResponse.Builder().withReservation(reservation).build();
        return response;
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

    @ExceptionHandler({ResourceNotFoundException.class, NoSuchElementException.class, UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleException(RuntimeException e) {
        ErrorResponse response = new ErrorResponse.Builder().withCause(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
