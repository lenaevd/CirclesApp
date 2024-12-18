package app.circles.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue
    private UUID id;

    @NotEmpty(message = "Name can't be empty")
    @Size(max = 50, min = 3, message = "Max size of name is 50 symbols")
    private String name;

    @Size(max = 500, min = 3, message = "Max size of description is 500 symbols")
    private String description;

    @Size(max = 100, min = 3, message = "Max size of time and place information is 100 symbols")
    private String timeAndPlaceInfo;

    private boolean isActive;

    @Digits(integer = 100, fraction = 0, message = "Max number is 100 members")
    private int maxMembersCount;

    private int membersCount;

    private String chatLink;

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
    public boolean AddMember(User user) {
        if (membersCount + 1 <= maxMembersCount)
        {
            members.add(user);
            membersCount++;

            return true;
        }

        return false;
    }

    public void RemoveMember(User user) {
        members.remove(user);
        membersCount--;
    }
}
