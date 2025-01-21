package app.circles.responses;

import app.circles.enums.RequestStatus;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class GetRequestResponse {
    public Integer id;
    public UUID eventId;
    public UUID userId;
    public RequestStatus status;
    public String imageUrl;
    public String name;
}
