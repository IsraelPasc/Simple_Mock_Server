package es.mockserver.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Map;

/**
 * DTO to decouple the database model mock object from the controller.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MockDTO {

    @Null
    private Long id;

    private String mockContent;

    @Min(100)
    @Max(599)
    private int httpStatus;

    private Map<String, String> headers;
}
