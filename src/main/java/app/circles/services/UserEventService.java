package app.circles.services;

import app.circles.models.Event;
import app.circles.models.User;
import app.circles.repos.EventRepository;
import app.circles.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserEventService {
    private final UserRepository userRepo;
    private final EventRepository eventRepo;

    @Autowired
    public UserEventService(
            UserRepository userRepo,
            EventRepository eventRepo) {

        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
    }

    public boolean addUserEvent(UUID userId, UUID eventId) {
        Optional<User> userOptional = userRepo.findById(userId);
        Optional<Event> eventOptional = eventRepo.findById(eventId);
        if (!(userOptional.isPresent() && eventOptional.isPresent())) {
            return false;
        }

        User user = userOptional.get();
        Event event = eventOptional.get();
        if (event.AddMember(user))
        {
            user.AddEvent(event);
            userRepo.save(user);
            eventRepo.save(event);

            return true;
        }

        return false;
    }

    public boolean removeUserEvent(UUID userId, UUID eventId){
        Optional<User> userOptional = userRepo.findById(userId);
        Optional<Event> eventOptional = eventRepo.findById(eventId);
        if (!(userOptional.isPresent() && eventOptional.isPresent())) {
            return false;
        }

        User user = userOptional.get();
        Event event = eventOptional.get();

        event.RemoveMember(user);
        user.RemoveEvent(event);
        userRepo.save(user);
        eventRepo.save(event);

        return true;
    }
}
