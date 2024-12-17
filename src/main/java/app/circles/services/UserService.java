package app.circles.services;

import app.circles.mappers.UserToGetUserResponseMapper;
import app.circles.models.User;
import app.circles.repos.UserRepository;
import app.circles.requests.EditUserRequest;
import app.circles.responses.GetUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final UserToGetUserResponseMapper mapper;

    @Autowired
    public UserService(
            UserRepository userRepo,
            UserToGetUserResponseMapper mapper) {
        this.userRepo = userRepo;
        this.mapper = mapper;
    }

    public void save(User user) {
        userRepo.save(user);
    }

    public GetUserResponse update(User user, EditUserRequest request)
    {
        try{
            user.setName(request.name);
            user.setSurname(request.surname);
            user.setBio(request.bio);
            user.setCity(request.city);
            user.setGender(request.gender);
            user.setDateOfBirth(request.dateOfBirth);
            user.setImageUrl(request.imageUrl);

            save(user);
            return mapper.Map(user);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public void delete(UUID userId) {
        userRepo.deleteById(userId);
    }

    public GetUserResponse getById(UUID userId) {
        Optional<User> user = userRepo.findById(userId);

        if (user.isEmpty())
        {
            return null;
        }

        return mapper.Map(user.get());
    }

    public Optional<User> getUser(UUID userId) {
        return userRepo.findById(userId);
    }

    public List<GetUserResponse> getAll() {
        List<User> users = userRepo.findAll();

        if (users.isEmpty())
        {
            return null;
        }

        List<GetUserResponse> response = new ArrayList<>();
        for (var user:
             users) {
            response.add(mapper.Map(user));
        }

        return response;
    }

}
