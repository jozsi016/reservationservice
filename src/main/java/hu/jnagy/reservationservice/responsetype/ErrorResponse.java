package hu.jnagy.reservationservice.responsetype;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Objects;

@JsonDeserialize(builder = ErrorResponse.Builder.class)
public final class ErrorResponse {
    private final String cause;

    private ErrorResponse(ErrorResponse.Builder builder) {
        this.cause = builder.cause;
    }

    public String getCause() {
        return cause;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponse that = (ErrorResponse) o;
        return Objects.equals(cause, that.cause);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cause);
    }

    public static class Builder {
        private String cause;

        public Builder withCause(String cause) {
            this.cause = cause;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }
    }
}
