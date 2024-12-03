package app.circles.services;

import app.circles.models.Event;
import app.circles.models.Type;
import app.circles.models.User;
import app.circles.repos.EventRepository;

import app.circles.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {
    private final EventRepository eventRepo;
    private final UserRepository userRepo;

    @Autowired
    public EventService(EventRepository eventRepo, UserRepository userRepo) {
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
    }

    public void save(Event event) {
        eventRepo.save(event);
    }

    public void deleteEventById(UUID eventId) {
        eventRepo.deleteById(eventId);
    }

    public void changeActive(UUID eventId, boolean isActive) {
        Optional<Event> eventOptional = eventRepo.findById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            event.setActive(isActive);
            eventRepo.save(event);
        }
    }

    public boolean createEvent(Event event, List<Type> types, UUID organizerId) {
        Optional<User> user = userRepo.findById(organizerId);
        if(user.isPresent()) {
            event.setOrganizer(user.get());
            event.setTypes(types);
            eventRepo.save(event);
            return true;
        } else {
            return false;
        }
    }

    public Optional<Event> getById(UUID eventId) {
        return eventRepo.findById(eventId);
    }

    public List<Event> getAll() {
        return eventRepo.findAll();
    }

    public List<Event> getActive() {
        return eventRepo.findByIsActive(true);
    }

    public List<Event> getAllByTypes(List<Type> types) {
        return eventRepo.findAllByTypesIn(types);
    }

    public List<Event> getActiveByTypes(List<Type> types) {
        return eventRepo.findByIsActiveAndTypesIn(true,types);
    }
}
