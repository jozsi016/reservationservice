package hu.jnagy.reservationservice.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import hu.jnagy.reservationservice.model.User;

import java.util.Objects;

@JsonDeserialize(builder = UserServiceResponse.Builder.class)
public final class UserServiceResponse {
    private final User user;

    private UserServiceResponse(UserServiceResponse.Builder builder) {
        this.user = builder.user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserServiceResponse that = (UserServiceResponse) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    @Override
    public String toString() {
        return "UserServiceResponse{" +
                "user=" + user +
                '}';
    }

    public static class Builder {
        private User user;

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public UserServiceResponse build() {
            return new UserServiceResponse(this);
        }
    }
}
