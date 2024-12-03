package app.circles.repos;

import app.circles.models.Event;
import app.circles.models.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    List<Event> findAllByTypesIn(List<Type> types);
//    Event findByTypeAndIsActive(boolean isActive);
    List<Event> findByIsActiveAndTypesIn(boolean isActive, List<Type> types);
    List<Event> findByIsActive(boolean isActive);

}
