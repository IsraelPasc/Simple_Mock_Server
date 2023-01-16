package es.mockserver.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Error pattern class.
 * Objects will be shown as having <b>status</b>, <b>date</b> and <b>message</b>.
 */
@Getter @Setter @RequiredArgsConstructor @NoArgsConstructor
public class ApiError {
    
    @NonNull
    private HttpStatus status;
    @JsonFormat(shape = Shape.STRING, pattern="dd/MM/yyyy hh:mm:ss")
    private LocalDateTime date = LocalDateTime.now();
    @NonNull
    private String message;

}
