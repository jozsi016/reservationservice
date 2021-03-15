package hu.jnagy.reservationservice.model;

import java.time.LocalDate;
import java.util.Objects;

public final class Reservation {
    private final long id;
    private final long userId;
    private final long roomId;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final double price;

    public Reservation(long id, long userId, long roomId, LocalDate startDate, LocalDate endDate, double price) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.startDate = startDate.plusDays(0);
        this.endDate = endDate.plusDays(0);
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getRoomId() {
        return roomId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id && userId == that.userId && roomId == that.roomId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, roomId);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id= " + id +
                ", userId= " + userId +
                ", roomId= " + roomId +
                ", startDate= " + startDate +
                ", endDate= " + endDate +
                ", price= " + price +
                " }";
    }
}
