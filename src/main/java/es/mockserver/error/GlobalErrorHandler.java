package es.mockserver.error;

import es.mockserver.error.exceptions.GenericException;
import es.mockserver.error.exceptions.MockNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Global error handler class for rest controllers.
 */
@RestControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    /**
     * Common errors method
     * @param ex Exception to handle.
     * @param body Body of the exception.
     * @param headers Headers passed with the exception.
     * @param status HttpStatus passed with the exception.
     * @param request Request of the exception.
     * @return ResponseEntity with the pattern of {@link ApiError}.
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(status, ex.getMessage());
        return ResponseEntity.status(status).headers(headers).body(apiError);
    }

    /**
     * Handles validation errors.
     * @param ex The thrown exception.
     * @return The {@link ApiError representation} returned.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        Map<String, String> filteredErrors = new HashMap<>();

        // Error fields can appear duplicated, so we have to filter them first
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            if (filteredErrors.containsKey(error.getField()))
                continue;
            filteredErrors.put(error.getField(), error.getDefaultMessage());
        }

        StringBuilder errors = new StringBuilder();
        filteredErrors.forEach((key, value) -> errors.append(key).append(" ").append(value).append(", "));

        String errorString = errors.replace(errors.length() - 2, errors.length(), ".").toString().trim();

        return ResponseEntity.status(status)
                .body(new ApiError(status, errorString));
    }

    /**
     * Handles errors that trigger when no mock was found in the database or files.
     * @param ex The thrown exception.
     * @return The {@link ApiError representation} returned.
     */
    @ExceptionHandler(MockNotFoundException.class)
    public ResponseEntity<ApiError> handleMockNotFoundException(MockNotFoundException ex) {
        return buildErrorResponseEntity(ex.getStatus(), ex.getMessage());
    }

    /**
     * Handles generic errors.
     * @param ex The thrown exception.
     * @return The {@link ApiError representation} returned.
     */
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ApiError> handleGenericException(GenericException ex) {
        return buildErrorResponseEntity(ex.getStatus(), ex.getMessage());
    }

    /**
     * Common method to create exceptions from ApiError.
     * @param status The http status to return in the error.
     * @param message The message to show in the error if any.
     */
    private ResponseEntity<ApiError> buildErrorResponseEntity(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(new ApiError(status, message));
    }
}
