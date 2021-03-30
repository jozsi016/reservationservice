package hu.jnagy.reservationservice.api.roomservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class ApiRoom {
    private final long id;
    private final double unitPrice;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ApiRoom(@JsonProperty("id")long id, @JsonProperty("unitPrice")double unitPrice) {
        this.id = id;
        this.unitPrice = unitPrice;
    }

    public long getId() {
        return id;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiRoom room = (ApiRoom) o;
        return id == room.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Room{ " + "id= " + id + ", unitPrice= " + unitPrice + " }";
    }
}
