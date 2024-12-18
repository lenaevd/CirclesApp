package app.circles.services;

import app.circles.mappers.EventToGetEventResponseMapper;
import app.circles.models.Event;
import app.circles.models.Type;
import app.circles.models.User;
import app.circles.repos.EventRepository;

import app.circles.repos.UserRepository;
import app.circles.responses.GetEventResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {
    private final EventRepository eventRepo;
    private final UserRepository userRepo;
    private final EventToGetEventResponseMapper mapper;

    @Autowired
    public EventService(EventRepository eventRepo, UserRepository userRepo, EventToGetEventResponseMapper mapper) {
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
        this.mapper = mapper;
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
        Optional<User> org = userRepo.findById(organizerId);
        if (org.isPresent()) {
            event.setActive(true);
            event.setOrganizer(org.get());
            event.setTypes(types);
            event.AddMember(org.get());
            eventRepo.save(event);
            return true;
        } else {
            return false;
        }
    }

    public GetEventResponse getById(UUID eventId) {
        Optional<Event> event = eventRepo.findById(eventId);
        if (event.isEmpty()) {
            return null;
        } else {
            return mapper.Map(event.get());
        }
    }

    public List<GetEventResponse> getAll() {
        List<Event> events =  eventRepo.findAll();
        List<GetEventResponse> mappedEvents = new ArrayList<>();

        for (var event:
                events) {
            mappedEvents.add(mapper.Map(event));
        }
        return  mappedEvents;
    }

    public List<Event> getActive() {
        return eventRepo.findByIsActive(true);
    }

    public List<Event> getAllByTypes(List<Type> types) {
        return eventRepo.findAllByTypesIn(types);
    }

    public List<Event> getActiveByTypes(List<Type> types) {
        return eventRepo.findByIsActiveAndTypesIn(true, types);
    }
}
