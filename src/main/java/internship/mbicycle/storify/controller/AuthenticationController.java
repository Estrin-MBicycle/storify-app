package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController()
@RequestMapping("/token")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final TokenService tokenService;

    @GetMapping("/refresh")
    public void refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        StorifyUser storifyUser = tokenService.getStorifyUserByRefreshToken(request);
        response.setHeader("access_token", storifyUser.getToken().getAccessToken());
        response.setHeader("refresh_token", storifyUser.getToken().getRefreshToken());
    }
}
