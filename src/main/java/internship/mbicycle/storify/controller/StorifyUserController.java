package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.service.StorifyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StorifyUserController {

    private final StorifyUserService userService;

    @GetMapping("/users")
    public List<StorifyUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/signUp")
    public StorifyUser addNewUser(StorifyUser storifyUser) {
        userService.saveUser(storifyUser);
        return userService.getUserById(storifyUser.getId());
    }

    @GetMapping("/activate/{code}")
    public String activateEmail(@PathVariable String code) {
        boolean isActivated = userService.activateEmail(code);
        if (isActivated) {
            return "Thank you for registration!";
        } else {
            return "Activate was not successful";
        }

    }

}
