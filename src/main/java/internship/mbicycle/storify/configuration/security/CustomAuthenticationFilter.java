package internship.mbicycle.storify.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.service.StorifyUserService;
import internship.mbicycle.storify.service.TokenService;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

@Component
@Lazy
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final TokenService tokenService;
    private final StorifyUserService userService;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, TokenService tokenService, StorifyUserService userService) {
        setAuthenticationManager(authenticationManager);
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Authentication authentication = null;
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
            authentication = getAuthenticationManager().authenticate(authenticationToken);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(TEXT_HTML_VALUE);
            new ObjectMapper().writeValue(response.getWriter(), e.getMessage());
        }
        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        StorifyUser storifyUser = userService.getUserByEmail(user.getUsername());
        String accessToken = tokenService.createAccessToken(storifyUser);
        String refreshToken = tokenService.createRefreshToken(storifyUser);
        tokenService.saveTokenPair(storifyUser, accessToken, refreshToken);
        response.setHeader("access_token", accessToken);
        response.setHeader("refresh_token", refreshToken);
    }
}
