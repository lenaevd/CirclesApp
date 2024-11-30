package app.circles.controllers;

import app.circles.requests.UserEventRequest;
import app.circles.services.UserEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/usersevents")
public class UsersEventController {
    private final UserEventService userEventService;

    @Autowired
    public UsersEventController(UserEventService userEventService) {
        this.userEventService = userEventService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createUserEvent(@RequestBody UserEventRequest request) {
        if (userEventService.addUserEvent(request.userId, request.eventId))
        {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeUserEvent(@RequestBody UserEventRequest request) {
        if (userEventService.removeUserEvent(request.userId, request.eventId))
        {
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
