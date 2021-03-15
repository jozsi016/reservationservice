package hu.jnagy.reservationservice.responsetype;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import hu.jnagy.reservationservice.model.Reservation;

import java.util.Objects;

@JsonDeserialize(builder = ReservationResponse.Builder.class)
public final class ReservationResponse {
    private final Reservation reservation;

    private ReservationResponse(ReservationResponse.Builder builder) {
        this.reservation =  builder.reservation;
    }

    public Reservation getReservation() {
        return reservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationResponse that = (ReservationResponse) o;
        return Objects.equals(reservation, that.reservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservation);
    }

    @Override
    public String toString() {
        return "ReservationResponse{" +
                "reservation=" + reservation +
                '}';
    }

    public static class Builder {
        private Reservation reservation;

        public Builder withReservation(Reservation reservation) {
            this.reservation = reservation;
            return this;
        }

        public ReservationResponse build() {
            return  new ReservationResponse(this);
        }

    }
}
