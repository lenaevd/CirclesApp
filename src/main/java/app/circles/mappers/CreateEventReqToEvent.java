package app.circles.mappers;

import app.circles.models.Event;
import app.circles.requests.CreateEventRequest;
import org.springframework.stereotype.Component;

@Component
public class CreateEventReqToEvent {
    public Event Map(CreateEventRequest request) {
        Event event = new Event();
        event.setName(request.name);
        event.setDescription(request.description);
        event.setTimeAndPlaceInfo(request.timeAndPlaceInfo);
        event.setChatLink(request.chatLink);
        event.setMaxMembersCount(request.maxMembersCount);
        event.setMembersCount(0);
        event.setImageUrl(request.imageUrl);
        // + organizer, types, members
        return event;
    }
}
