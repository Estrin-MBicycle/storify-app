package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController()
@RequestMapping("/token")
@RequiredArgsConstructor
public class AuthenticationController {

    private final TokenService tokenService;

    @GetMapping("/refresh")
    public void getNewAccessToken(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> authorizationHeader = Optional.ofNullable(request.getHeader(AUTHORIZATION));
        String header = authorizationHeader.orElseThrow(() -> new RuntimeException("The token is not correct"));
        StorifyUser storifyUser = tokenService.getTokensFromRefreshToken(header);
        response.setHeader("access_token", storifyUser.getToken().getAccessToken());
        response.setHeader("refresh_token", storifyUser.getToken().getRefreshToken());
    }
}
