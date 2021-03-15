package hu.jnagy.reservationservice.repository;

import hu.jnagy.reservationservice.model.Reservation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class ReservationRepository {
    private final Map<Long, Reservation> reservations = new HashMap<>();

    public Map<Long, Reservation> getReservations() {
        return reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.put(reservation.getId(), reservation);
    }
}
