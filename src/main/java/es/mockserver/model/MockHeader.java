package es.mockserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Database entry representation for mock response header objects.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MockHeader implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String headerKey;
    private String headerValue;
}
