package app.circles.mappers;

import app.circles.models.Type;
import app.circles.models.User;
import app.circles.responses.GetUserResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
                user.getImageUrl(),
                getStringFromInterests(user.getInterests()));
    }

    private List<String> getStringFromInterests(List<Type> types) {
        List<String> interestsList = new ArrayList<>();
        for (Type type: types) {
            interestsList.add(type.getName());
        }
        return interestsList;
    }
}
