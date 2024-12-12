package app.circles.requests;

import app.circles.enums.Gender;
import app.circles.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EditUserRequest {

    @Size(max = 50, message = "Name cannot exceed 50 characters")
    public String name;

    @Size(max = 60, message = "Surname cannot exceed 50 characters")
    public String surname;

    @Size(max = 500, message = "Bio cannot exceed 500 characters")
    public String bio;

    @Size(max = 100, message = "City cannot exceed 100 characters")
    public String city;

    public String imageUrl;

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate dateOfBirth;

    //public Role role;

    public Gender gender;
}
