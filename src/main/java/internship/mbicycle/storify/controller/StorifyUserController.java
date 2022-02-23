package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.dto.StorifyUserDTO;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.service.StorifyUserService;
import internship.mbicycle.storify.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StorifyUserController {

    private final StorifyUserService userService;
    private final TokenService tokenService;

    @PostMapping("/sign-up")
    public StorifyUserDTO addNewUser(@Valid @RequestBody StorifyUserDTO storifyUserDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new StorifyUserDTO();
        } else {
            return storifyUserDTO;
        }
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
