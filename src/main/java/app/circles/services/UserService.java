package app.circles.services;

import app.circles.models.Event;
import app.circles.models.User;
import app.circles.repos.EventRepository;
import app.circles.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public void save(User user) {
        userRepo.save(user);
    }

    public void delete(UUID userId) {
        userRepo.deleteById(userId);
    }

    public Optional<User> getById(UUID userId) {
        return userRepo.findById(userId);
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

}
