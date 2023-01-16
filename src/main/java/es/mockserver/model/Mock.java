package es.mockserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Database entry representation for mock objects.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Mock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String response;

    @Min(100)
    @Max(599)
    private int responseStatus;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mock_id")
    private List<MockHeader> responseHeaders;
}
