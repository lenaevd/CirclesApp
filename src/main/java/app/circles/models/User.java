package app.circles.models;

import app.circles.enums.Gender;
import app.circles.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

//@Getter
//@Setter

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data //new
@Builder
@Table(name = "users")
public class User implements UserDetails {
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

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private boolean isActive;

    private String imageUrl;

    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER)
    private List<Event> events = new ArrayList<>();

    public void AddEvent(Event event) {events.add(event);}

    public void RemoveEvent(Event event) {events.remove(event);}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return name; //OR MAYBE EMAIL?
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
