package app.circles.responses;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class GetEventResponse {
    public UUID id;
    public String name;
    public String description;
    public String timeAndPlaceInfo;
    public boolean isActive;
    public int maxMembersCount;
    public int membersCount;
    public String chatLink;
    public String imageUrl;
    public UUID organizerId;
    public List<MemberInfo> members;
}
