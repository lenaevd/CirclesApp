package app.circles.mappers;

import app.circles.models.Event;
import app.circles.models.Type;
import app.circles.models.User;
import app.circles.repos.EventRepository;
import app.circles.repos.UserRepository;
import app.circles.responses.GetEventResponse;
import app.circles.responses.GetUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserToGetUserResponseMapper {

    private final EventToGetEventResponseMapper eventMapper;

    @Autowired
    public UserToGetUserResponseMapper(EventToGetEventResponseMapper mapper) {
        this.eventMapper = mapper;
    }

    public GetUserResponse Map(User user)
    {
        if (user == null)
        {
            return null;
        }

        List<Event> events = user.getEvents();
        List<GetEventResponse> mappedEvents = new ArrayList<>();

        for (var event:
             events) {
            mappedEvents.add(eventMapper.Map(event));
        }

        return new GetUserResponse(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getBio(),
                user.getCity(),
                user.getEmail(),
                user.getGender(),
                user.getDateOfBirth().toString(),
                user.isActive(),
                user.getImageUrl(),
                getStringFromInterests(user.getInterests()),
                mappedEvents);
    }

    private List<String> getStringFromInterests(List<Type> types) {
        List<String> interestsList = new ArrayList<>();
        for (Type type: types) {
            interestsList.add(type.getName());
        }
        return interestsList;
    }
}
