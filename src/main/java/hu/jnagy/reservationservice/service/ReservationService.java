package hu.jnagy.reservationservice.service;

import hu.jnagy.reservationservice.api.RoomServiceResponse;
import hu.jnagy.reservationservice.model.Reservation;
import hu.jnagy.reservationservice.model.Room;
import hu.jnagy.reservationservice.model.User;
import hu.jnagy.reservationservice.repository.ReservationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    public long reservationId = 1;
    private ReservationRepository reservationRepository;
    private RestTemplate restTemplate;

    private String roomServiceURL = "http://localhost:8082/rooms";

    public ReservationService(ReservationRepository reservationRepository, RestTemplate restTemplate) {
        this.reservationRepository = reservationRepository;
        this.restTemplate = restTemplate;
    }

    public void createReservation(User user, Room room, LocalDate start, LocalDate end) {
        Period startDay = Period.of(start.getYear(), start.getMonthValue(), start.getDayOfMonth());
        Period endDay = Period.of(end.getYear(), end.getMonthValue(), end.getDayOfMonth());
        int numberOfStay = endDay.getDays() - startDay.getDays();
        double price = room.getUnitPrice() * numberOfStay;
        Reservation reservation = new Reservation(reservationId, user.getId(), room.getId(), start, end, price);
        reservationRepository.addReservation(reservation);
        reservationId++;
    }

    public void deleteReservation(long reservationId) {
        reservationRepository.getReservations().remove(reservationId);
    }

    public List<Room> listOfAvailableRooms(LocalDate start, LocalDate end) {

        ResponseEntity<RoomServiceResponse> roomResponse = restTemplate.getForEntity(roomServiceURL, RoomServiceResponse.class);
        List<Room> rooms = roomResponse.getBody().getRooms();
        Predicate<Reservation> isNotAvailableInGivemTime =
                r -> (r.getStartDate().compareTo(start) >= 0 && r.getEndDate().compareTo(start) >= 0) ||
                        (r.getStartDate().compareTo(end) >= 0 && r.getEndDate().compareTo(end) >= 0);
        List<Long> reservations = reservationRepository.getReservations().values().stream()
                .filter(isNotAvailableInGivemTime).map(r-> r.getRoomId()).collect(Collectors.toList());

        return rooms.stream().filter(room-> reservations.contains(room.getId())).collect(Collectors.toList());
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
        String delimater = ":";
        String[] parsedDate = date.split(delimater);
        Integer year = Integer.valueOf(parsedDate[0]);
        Integer month = Integer.valueOf(parsedDate[1]);
        Integer dayOfMonth = Integer.valueOf(parsedDate[2]);
        return LocalDate.of(year, month, dayOfMonth);
    }
}

