package userManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Handle user Signup,
    // the parameters sent from user are username, email and password.
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Map<String, String> data) {
        return userService.signup(data);
    }

    // Handle user Login,
    // the parameters sent by user are email, password, hostname, port
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> data) {
        return userService.login(data);
    }

    // Handle user logout
    // notify the server which node has exited.
    @PostMapping("/logout")
    public void logout(@RequestBody Long userId) {
        userService.logout(userId);
    }
}

