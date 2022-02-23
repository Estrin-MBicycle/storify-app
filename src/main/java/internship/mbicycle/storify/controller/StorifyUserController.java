package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.dto.StorifyUserDTO;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.service.StorifyUserService;
import internship.mbicycle.storify.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.security.Principal;

import static internship.mbicycle.storify.util.ExceptionMessage.EMPTY_EMAIL_EXCEPTION;
import static internship.mbicycle.storify.util.ExceptionMessage.INVALID_EMAIL_EXCEPTION;

@RestController
@RequiredArgsConstructor
@Validated
public class StorifyUserController {

    private final StorifyUserService userService;
    private final TokenService tokenService;

    @PostMapping("/sign-up")
    public void addNewUser(@Valid @RequestBody StorifyUserDTO storifyUserDTO) {
        userService.saveNewUser(storifyUserDTO);
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
    public void updateEmail(
            @NotBlank(message = EMPTY_EMAIL_EXCEPTION) @Email(message = INVALID_EMAIL_EXCEPTION) String newEmail,
            @PathVariable String code, Principal principal) {
        userService.updateEmail(newEmail, code, principal.getName());
    }

}
