package app.circles.responses;

import app.circles.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter

@AllArgsConstructor
public class GetUserResponse {
    public UUID id;

    public String name;

    public String surname;

    public String bio;

    public String city;

    public String email;

    public Gender gender;

    public String dateOfBirth;

    public boolean isActive;

    public String imageUrl;

}
