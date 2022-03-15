package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.dto.LoginDTO;
import internship.mbicycle.storify.dto.NewEmailDTO;
import internship.mbicycle.storify.dto.StorifyUserDTO;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.service.StorifyUserService;
import internship.mbicycle.storify.service.TokenService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@RestController
@RequiredArgsConstructor
public class StorifyUserController {

    private final StorifyUserService userService;
    private final TokenService tokenService;

    @ApiOperation("User registration")
    @PostMapping("/sign-up")
    public void addNewUser(@Valid @RequestBody StorifyUserDTO storifyUserDTO) {
        userService.saveNewUser(storifyUserDTO);
    }

    @ApiOperation("User authentication")
    @PostMapping("/login")
    public void login(@Valid @RequestBody LoginDTO loginDTO) {
    }

    @ApiOperation("Confirmation of registration")
    @GetMapping("/activate/{code}")
    public void activateEmail(@PathVariable String code, HttpServletRequest request, HttpServletResponse response) {
        StorifyUser storifyUser = userService.activateUserByEmail(code);
        String userAgent = request.getHeader(USER_AGENT);
        tokenService.setTokenPair(storifyUser, userAgent, response);
    }

    @ApiOperation("Send confirmation to email")
    @GetMapping("/update/email")
    public void sendConfirmationEmail(@ApiIgnore Principal principal) {
        userService.sendConfirmationEmail(principal.getName());
    }

    @ApiOperation("Changed email")
    @PatchMapping("/update/{code}")
    public void updateEmail(@Valid @RequestBody NewEmailDTO newEmailDTO,
                            @PathVariable String code,
                            @ApiIgnore Principal principal,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        StorifyUser storifyUser = userService.updateEmail(newEmailDTO.getEmail(), code, principal.getName());
        String userAgent = request.getHeader(USER_AGENT);
        tokenService.setTokenPair(storifyUser, userAgent, response);
    }

}
