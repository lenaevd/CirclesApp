package app.circles.services;

import app.circles.models.Event;
import app.circles.models.Type;
import app.circles.repos.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {
    private final EventRepository eventRepo;

    @Autowired
    public EventService(EventRepository eventRepo) {
        this.eventRepo = eventRepo;
    }

    public void save(Event event) {
        eventRepo.save(event);
    }

    public void deleteEventById(UUID eventId) {
        eventRepo.deleteById(eventId);
    }

    public Optional<Event> getById(UUID eventId) {
        return eventRepo.findById(eventId);
    }

    public List<Event> getAll() {
        return eventRepo.findAll();
    }

    public List<Event> getByTypes(List<Type> types) {
        List<Event> events = new ArrayList<>();
        for (Type type: types) {
            events.add(eventRepo.findByType(type));
        }
        return events;
    }
}
