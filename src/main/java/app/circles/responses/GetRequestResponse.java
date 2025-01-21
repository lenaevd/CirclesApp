package app.circles.responses;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class GetRequestResponse {
    public Integer id;
    public UUID eventId;
    public UUID userId;
    public boolean isAccepted;
    public String imageUrl;
    public String name;
}
