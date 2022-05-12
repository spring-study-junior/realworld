package springstudyjunior.realworld.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import springstudyjunior.realworld.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/api/users")
    public void registerUser() {
        userService.registerUser();
    }

    @PostMapping("/api/users/login")
    public void loginUser() {
        userService.loginUser();
    }

    @GetMapping("/api/user")
    public void getUser() {
        userService.getUser();
    }

    @PutMapping("/api/user")
    public void updateUser() {
        userService.updateUser();
    }

}
