package internship.mbicycle.storify.controller;

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
        if (authorizationHeader.isPresent()) {
            response.setHeader("access_token", tokenService.getAccessTokenFromRefreshToken(authorizationHeader.get()));
            response.setHeader("refresh_token", authorizationHeader.get().substring("Bearer ".length()));
        }
    }
}
