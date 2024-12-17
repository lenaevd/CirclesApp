package app.circles.controllers;

import app.circles.models.User;
import app.circles.requests.EditUserRequest;
import app.circles.responses.GetUserResponse;
import app.circles.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get")
    public ResponseEntity<GetUserResponse> getUserById(@RequestParam UUID userId) {
        GetUserResponse user = userService.getById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<GetUserResponse>> getAllUsers() {
        List<GetUserResponse> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/update")
    public ResponseEntity<GetUserResponse> editUser(@RequestParam UUID userId, @RequestBody EditUserRequest request) {
        Optional<User> user = userService.getUser(userId);
        if (user.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }

        GetUserResponse response = userService.update(user.get(), request);

        if (response == null)
        {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(response);
        }
    }
}
