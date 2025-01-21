package app.circles.models;

import app.circles.enums.RequestStatus;
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
    private RequestStatus status;

    public Request(UUID eventId, UUID userId) {
        this.eventId = eventId;
        this.userId = userId;
        this.status = RequestStatus.REVIEWING;
    }
}
