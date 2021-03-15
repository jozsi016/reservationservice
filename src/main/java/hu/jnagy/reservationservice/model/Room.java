package hu.jnagy.reservationservice.model;

import java.util.Objects;

public final class Room {
    private final long id;
    private final double unitPrice;

    public Room(long id, double unitPrice) {
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
        Room room = (Room) o;
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
