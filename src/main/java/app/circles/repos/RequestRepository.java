package app.circles.repos;

import app.circles.enums.RequestStatus;
import app.circles.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> findByEventIdAndStatus(UUID eventId, RequestStatus status);
    Optional<Request> findByEventIdAndUserId(UUID eventId, UUID userId);
}
