package app.circles.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String description;
    private String extraInfo; //TODO: может быть нужен город?
    private boolean isActive;
    private int maxMembersCount;
    private int membersCount;
    private String imageUrl;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "event_organizers",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private User organizer;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "event_members",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> members = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "event_types",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    private List<Type> types = new ArrayList<>();

    //private List<Review> reviews;
    public boolean AddUser(User user) {
        if (membersCount + 1 <= maxMembersCount)
        {
            members.add(user);
            membersCount++;

            return true;
        }

        return false;
    }

    public void RemoveUser(User user) {
        members.remove(user);
        membersCount--;
    }
}
