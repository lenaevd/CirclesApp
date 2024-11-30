package app.circles.models;

import app.circles.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter

@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    @NotEmpty(message = "Name can't be empty")
    @Size(max = 20, min = 3, message = "Wrong size of name provided")
    private String name;

    @NotEmpty(message = "Surname can't be empty")
    @Size(max = 20, min = 2, message = "Wrong size of surname provided")
    private String surname;

    @Size(max = 500, message = "Max size of bio is 500 symbols")
    private String bio;

    @Size(max = 100)
    private String city;

    private String email;

    private Gender gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private boolean isActive;

    private String imageUrl;

    private String login;

    private String password;

    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER)
    private List<Event> events = new ArrayList<>();

    public void AddEvent(Event event) {events.add(event);}

    public void RemoveEvent(Event event) {events.remove(event);}
}
