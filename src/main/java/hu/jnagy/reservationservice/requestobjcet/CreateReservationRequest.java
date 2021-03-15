package hu.jnagy.reservationservice.requestobjcet;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CreateReservationRequest.Builder.class)
public class CreateReservationRequest {
    private final long userId;
    private final long roomId;
    private final String startStr;
    private final String endStr;

    public CreateReservationRequest(long userId, long roomId, String startStr, String endStr) {
        this.userId = userId;
        this.roomId = roomId;
        this.startStr = startStr;
        this.endStr = endStr;
    }

    public long getUserId() {
        return userId;
    }

    public long getRoomId() {
        return roomId;
    }

    public String getStartStr() {
        return startStr;
    }

    public String getEndStr() {
        return endStr;
    }

    @JsonPOJOBuilder
     public static class Builder {
        private long userId;
        private long roomId;
        private String startStr;
        private String endStr;

        public Builder withUserId(long userId) {
            this.userId = userId;
            return this;
        }

        public Builder withRoomId(long roomId) {
            this.roomId = roomId;
            return this;
        }

        public Builder withStartStr(String startStr) {
            this.startStr = startStr;
            return this;
        }

        public Builder withEndStr(String endStr) {
            this.endStr = endStr;
            return this;
        }

        public CreateReservationRequest build() {
            return new CreateReservationRequest(userId,roomId,startStr,endStr);
        }

    }
}
