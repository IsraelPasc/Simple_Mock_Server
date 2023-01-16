package es.mockserver.repository;

import es.mockserver.model.Mock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class for {@link Mock} objects.
 */
@Repository
public interface MockRepository extends JpaRepository<Mock, Long> {

}
