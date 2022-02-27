package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.service.StorifyUserService;
import internship.mbicycle.storify.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class StorifyUserController {

    private final StorifyUserService userService;
    private final TokenService tokenService;

    @PostMapping("/sign-up")
    public void addNewUser(StorifyUser storifyUser) {
        userService.saveNewUser(storifyUser);
    }

    @GetMapping("/activate/{code}")
    public void activateEmail(@PathVariable String code, HttpServletResponse response) {
        StorifyUser storifyUser = userService.activateUserByEmail(code);
        tokenService.setTokenPairAfterActivation(storifyUser);
        response.setHeader("access_token", storifyUser.getToken().getAccessToken());
        response.setHeader("refresh_token", storifyUser.getToken().getRefreshToken());
    }

    @GetMapping("/update/email")
    public void sendConfirmationEmail(Principal principal) {
        userService.sendConfirmationEmail(principal.getName());
    }

    @PatchMapping("/update/{code}")
    public void updateEmail(String newEmail, @PathVariable String code, Principal principal) {
        userService.updateEmail(newEmail, code, principal.getName());
    }

}
