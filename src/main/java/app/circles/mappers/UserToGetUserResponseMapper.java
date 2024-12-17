package app.circles.mappers;

import app.circles.models.User;
import app.circles.responses.GetUserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserToGetUserResponseMapper {

    public GetUserResponse Map(User user)
    {
        if (user == null)
        {
            return null;
        }

        return new GetUserResponse(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getBio(),
                user.getCity(),
                user.getEmail(),
                user.getGender(),
                user.getDateOfBirth(),
                user.isActive(),
                user.getImageUrl());
    }
}
