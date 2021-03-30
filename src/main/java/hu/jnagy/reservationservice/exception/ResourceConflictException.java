package hu.jnagy.reservationservice.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Occupied room!")  // 404
public class ResourceConflictException extends RuntimeException {
    @JsonCreator
    public ResourceConflictException(String message) {
        super(message);
    }
}
