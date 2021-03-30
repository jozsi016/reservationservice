package hu.jnagy.reservationservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Objects;

public final class Reservation {
    private long id;
    private long userId;
    private long roomId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private double price;


    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Reservation(@JsonProperty("id") long id, @JsonProperty("userId") long userId,
                       @JsonProperty("roomId") long roomId,
                       @JsonProperty("startDate") LocalDate startDate,
                       @JsonProperty("endDate") LocalDate endDate,
                       @JsonProperty("price") double price) {
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
        return id == that.id && userId == that.userId && roomId == that.roomId &&
                Double.compare(that.price, price) == 0 && startDate.equals(that.startDate) && endDate.equals(that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, roomId, startDate, endDate, price);
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
