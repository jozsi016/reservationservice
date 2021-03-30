package hu.jnagy.reservationservice.api;

import hu.jnagy.reservationservice.api.roomservice.ApiRoom;
import hu.jnagy.reservationservice.api.userservice.ApiUser;
import hu.jnagy.reservationservice.model.Room;
import hu.jnagy.reservationservice.model.User;

import java.util.List;
import java.util.stream.Collectors;

public final class ApiConverter {

    public ApiConverter() {
    }

    public User convertToUser(ApiUser apiUser) {
        return new User(apiUser.getId(), apiUser.getName());
    }

    public Room convertToRoom(ApiRoom apiRoom) {
        return new Room(apiRoom.getId(), apiRoom.getUnitPrice());
    }

    public List<Room> convertToRooms(List<ApiRoom> rooms) {
        return rooms.stream().map(r -> new Room(r.getId(), r.getUnitPrice())).collect(Collectors.toList());
    }
}
