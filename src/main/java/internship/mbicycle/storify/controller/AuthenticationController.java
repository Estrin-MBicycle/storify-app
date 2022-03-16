package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController()
@RequestMapping("/token")
@RequiredArgsConstructor
public class AuthenticationController {

    private final TokenService tokenService;

    @GetMapping("/refresh")
    public void refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        tokenService.setTokenPairByRefreshToken(request, response);
    }
}