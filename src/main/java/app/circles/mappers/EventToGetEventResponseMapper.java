package app.circles.mappers;

import app.circles.models.Event;
import app.circles.models.User;
import app.circles.responses.GetEventResponse;
import app.circles.responses.MemberInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventToGetEventResponseMapper {
    public GetEventResponse Map(Event event) {
        if (event == null) {
            return null;
        }
        return new GetEventResponse(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getTimeAndPlaceInfo(),
                event.isActive(),
                event.getMaxMembersCount(),
                event.getMembersCount(),
                event.getChatLink(),
                event.getImageUrl(),
                event.getOrganizer().getId(),
                createMemberInfo(event.getMembers())
        );
    }

    private List<MemberInfo> createMemberInfo(List<User> members) {
        List<MemberInfo> memberInfoList = new ArrayList<>();
        for (User user: members) {
            memberInfoList.add(new MemberInfo(user.getId(), user.getName(), user.getImageUrl()));
        }
        return memberInfoList;
    }
}
