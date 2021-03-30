package hu.jnagy.reservationservice.service;

import hu.jnagy.reservationservice.api.roomservice.ApiRoom;
import hu.jnagy.reservationservice.api.roomservice.RoomServiceResponse;
import hu.jnagy.reservationservice.configuration.ReservationConfiguration;
import hu.jnagy.reservationservice.api.ApiConverter;
import hu.jnagy.reservationservice.model.Reservation;
import hu.jnagy.reservationservice.model.Room;
import hu.jnagy.reservationservice.model.User;
import hu.jnagy.reservationservice.repository.ReservationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    public long reservationId = 1;
    private ReservationRepository reservationRepository;
    private RoomService roomService;
    private ReservationConfiguration configuration;
    private ApiConverter apiConverter;

    public ReservationService(ReservationRepository reservationRepository, RoomService roomService, ReservationConfiguration configuration) {
        this.reservationRepository = reservationRepository;
        this.roomService = roomService;
        this.configuration = configuration;
        this.apiConverter = new ApiConverter();
    }

    public Reservation createReservation(User user, Room room, LocalDate start, LocalDate end) {
        Period startDay = Period.of(start.getYear(), start.getMonthValue(), start.getDayOfMonth());
        Period endDay = Period.of(end.getYear(), end.getMonthValue(), end.getDayOfMonth());
        int numberOfStay = endDay.getDays() - startDay.getDays();
        double price = room.getUnitPrice() * numberOfStay;
        Reservation reservation = new Reservation(reservationId, user.getId(), room.getId(), start, end, price);
        reservationRepository.addReservation(reservation);
        reservationId++;
        return reservation;
    }

    public void deleteReservation(long reservationId) {
        reservationRepository.getReservations().remove(reservationId);
    }

    public List<Room> listOfAvailableRooms(LocalDate start, LocalDate end) {
        ResponseEntity<RoomServiceResponse> roomResponse = roomService.getRooms();
        List<ApiRoom> apiRooms = roomResponse.getBody().getRooms();
        List<Room> rooms = apiConverter.convertToRooms(apiRooms);
        Predicate<Reservation> isNotAvailableInGivenTime =
                r -> (r.getStartDate().compareTo(start) >= 0 && r.getEndDate().compareTo(start) >= 0) ||
                        (r.getStartDate().compareTo(end) >= 0 && r.getEndDate().compareTo(end) >= 0);
        List<Long> reservations = reservationRepository.getReservations().values().stream()
                .filter(isNotAvailableInGivenTime).map(r-> r.getRoomId()).collect(Collectors.toList());
        return rooms.stream().filter(room-> !reservations.contains(room.getId())).collect(Collectors.toList());
    }

    public Reservation getReservationById(long reservationId) {
        return reservationRepository.getReservations().get(reservationId);
    }

    public List<Reservation> getAllUserReservationsByUserId(long userId) {
        return reservationRepository.getReservations().values().stream().filter(
                reservation -> userId == reservation.getUserId()).collect(Collectors.toList());
    }

    public List<Reservation> getFilteredReservation(Predicate<Reservation> predicate) {
        return reservationRepository.getReservations().values().
                stream().filter(predicate).collect(Collectors.toList());
    }

    public LocalDate getLocalDate(String date) {
       return LocalDate.parse(date);
    }
}

