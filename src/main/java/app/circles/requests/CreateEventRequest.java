package app.circles.requests;

import java.util.List;
import java.util.UUID;

public class CreateEventRequest {
    public String name;
    public String description;
    public String timeAndPlaceInfo;
    public String city;
    public boolean isActive;
    public int maxMembersCount;
    public String imageUrl;
    public UUID organizerId;
    public List<String> typesNames;
}
