package hu.jnagy.reservationservice.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The user is not in the system!")  // 404
public class ResourceNotFoundException extends RuntimeException {
    @JsonCreator
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
