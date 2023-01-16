package es.mockserver.error.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * General exceptions class.
 */
@Getter
public class GenericException extends RuntimeException {
    private final String message;
    private final HttpStatus status;

    public GenericException(String message) {
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
    }
}
