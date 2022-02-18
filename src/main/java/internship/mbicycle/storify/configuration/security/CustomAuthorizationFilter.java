package internship.mbicycle.storify.configuration.security;

import internship.mbicycle.storify.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!(request.getServletPath().equals("/login") ||
                request.getServletPath().equals("/sign-up") ||
                request.getServletPath().equals("/token/refresh") ||
                request.getServletPath().startsWith("/activate/"))) {
            String authorizationHeader = Optional.ofNullable(request.getHeader(AUTHORIZATION))
                    .orElseThrow(() -> new RuntimeException("The token not found"));
            UsernamePasswordAuthenticationToken authenticationToken =
                    tokenService.getAuthenticationToken(authorizationHeader);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
