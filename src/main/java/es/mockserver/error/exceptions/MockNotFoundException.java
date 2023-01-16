package es.mockserver.error.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Entry not found exception class.
 */
@Getter
public class MockNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Mock not found.";

    private final String message;
    private final HttpStatus status;

    public MockNotFoundException() {
        super(DEFAULT_MESSAGE);
        this.message = DEFAULT_MESSAGE;
        this.status = HttpStatus.NOT_FOUND;
    }

    public MockNotFoundException(String message) {
        super(message);
        this.message = message;
        this.status = HttpStatus.NOT_FOUND;
    }
}
