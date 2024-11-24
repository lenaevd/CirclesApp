package app.circles.repos;

import app.circles.models.Event;
import app.circles.models.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    Event findByType(Type type);
}
