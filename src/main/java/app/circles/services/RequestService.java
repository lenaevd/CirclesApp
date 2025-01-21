package app.circles.services;

import app.circles.enums.RequestStatus;
import app.circles.models.Event;
import app.circles.models.Request;
import app.circles.models.User;
import app.circles.repos.EventRepository;
import app.circles.repos.RequestRepository;
import app.circles.repos.UserRepository;
import app.circles.responses.GetRequestResponce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RequestService {
    private final RequestRepository requestRepo;
    private final EventRepository eventRepo;
    private final UserRepository userRepo;

    @Autowired
    public RequestService(RequestRepository requestRepo, EventRepository eventRepo, UserRepository userRepo) {
        this.requestRepo = requestRepo;
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
    }

    public boolean createRequest(UUID eventId, UUID userId) {
        Optional<User> user = userRepo.findById(userId);
        Optional<Event> event = eventRepo.findById(eventId);
        if (user.isPresent() && event.isPresent()) {
            Request request = new Request(eventId, userId, false);
            requestRepo.save(request);
            return true;
        } else {
            return false;
        }
    }

    public boolean acceptRequest(Integer requestId) {
        Optional<Request> requestOptional = requestRepo.findById(requestId);
        if (requestOptional.isPresent()) {
            Request request = requestOptional.get();
            addUserEvent(request.getUserId(), request.getEventId());
            request.setAccepted(true);
            requestRepo.save(request);
            return true;
        } else {
            return false;
        }
    }

    public boolean rejectRequest(Integer requestId) {
        Optional<Request> requestOptional = requestRepo.findById(requestId);
        if (requestOptional.isPresent()) {
            requestRepo.delete(requestOptional.get());
            return true;
        } else {
            return false;
        }
    }

    public List<GetRequestResponce> getRequests(UUID eventId) {
        List<Request> requests = requestRepo.findByEventIdAndIsAccepted(eventId, false);
        List<GetRequestResponce> list = new ArrayList<>();
        for (Request req: requests) {
            Optional<User> user = userRepo.findById(req.getUserId());
            String imageUrl = user.get().getImageUrl();
            String name = user.get().getName();
            GetRequestResponce responce = new GetRequestResponce(req.getId(), req.getEventId(), req.getUserId(), req.isAccepted(),
                    imageUrl, name);
            list.add(responce);
        }
        return list;
    }

    public RequestStatus checkStatus(UUID eventId, UUID userId) {
        Optional<Request> requestOptional = requestRepo.findByEventIdAndUserId(eventId, userId);
        if (requestOptional.isEmpty()) {
            return RequestStatus.NO_REQUEST;
        } else {
            if (requestOptional.get().isAccepted()) {
                return RequestStatus.ACCEPTED;
            } else {
                return RequestStatus.REVIEWING;
            }
        }
    }

    public boolean addUserEvent(UUID userId, UUID eventId) {
        Optional<User> userOptional = userRepo.findById(userId);
        Optional<Event> eventOptional = eventRepo.findById(eventId);
        if (!(userOptional.isPresent() && eventOptional.isPresent())) {
            return false;
        }
        User user = userOptional.get();
        Event event = eventOptional.get();
        if (event.AddMember(user)) {
            user.AddEvent(event);
            userRepo.save(user);
            eventRepo.save(event);
            return true;
        }
        return false;
    }
}
