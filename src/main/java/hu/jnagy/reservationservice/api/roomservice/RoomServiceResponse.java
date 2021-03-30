package hu.jnagy.reservationservice.api.roomservice;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;
import java.util.Objects;
//import hu.jnagy.reservationservice.model.Room;

@JsonDeserialize(builder = RoomServiceResponse.Builder.class)
public class RoomServiceResponse {

    private final List<ApiRoom> rooms;
    private RoomServiceResponse(RoomServiceResponse.Builder builder) {
        this.rooms = builder.rooms;
    }
    public List<ApiRoom> getRooms() {
        return rooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomServiceResponse that = (RoomServiceResponse) o;
        return Objects.equals(rooms, that.rooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rooms);
    }

    @Override
    public String toString() {
        return "RoomServiceResponse{" +
                "rooms=" + rooms +
                '}';
    }

    public static class Builder {
        private List<ApiRoom> rooms;

        public Builder withRooms(List<ApiRoom> rooms) {
            this.rooms = rooms;
            return this;
        }

        public RoomServiceResponse build() {
            return new RoomServiceResponse(this);
        }
    }


}
