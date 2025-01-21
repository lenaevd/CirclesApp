package app.circles.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue
    private Integer id;
    private UUID eventId;
    private UUID userId;
    private boolean isAccepted;

    public Request(UUID eventId, UUID userId, boolean isAccepted) {
        this.eventId = eventId;
        this.userId = userId;
        this.isAccepted = isAccepted;
    }
}
