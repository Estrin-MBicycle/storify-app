package internship.mbicycle.storify.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import internship.mbicycle.storify.configuration.security.parser.JwtParser;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final JwtParser jwtParser;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (!request.getRequestURI().equals("/token/refresh")) {
                jwtParser
                        .parseJwt(request)
                        .ifPresent(this::setJwtAuthentication);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(TEXT_HTML_VALUE);
            new ObjectMapper().writeValue(response.getWriter(), e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private void setJwtAuthentication(String token) {
        StorifyUser user = tokenService.getUserByAccessToken(token);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getEmail(), null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
