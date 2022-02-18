package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.service.StorifyUserService;
import internship.mbicycle.storify.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

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

}
