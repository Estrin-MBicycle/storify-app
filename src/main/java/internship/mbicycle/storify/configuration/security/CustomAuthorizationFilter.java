package internship.mbicycle.storify.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import internship.mbicycle.storify.exception.InvalidAuthorizationHeaderException;
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

import static internship.mbicycle.storify.util.ExceptionMessage.NOT_FOUND_AUTHORIZATION_HEADER;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!(request.getServletPath().equals("/login") ||
                request.getServletPath().equals("/sign-up") ||
                request.getServletPath().equals("/token/refresh") ||
                request.getServletPath().startsWith("/swagger") ||
                request.getServletPath().startsWith("/v2") ||
                request.getServletPath().startsWith("/activate/"))) {
            try {
                String authorizationHeader = Optional.ofNullable(request.getHeader(AUTHORIZATION))
                        .orElseThrow(() -> new InvalidAuthorizationHeaderException(NOT_FOUND_AUTHORIZATION_HEADER));
                UsernamePasswordAuthenticationToken authenticationToken =
                        tokenService.getAuthenticationToken(authorizationHeader);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType(TEXT_HTML_VALUE);
                new ObjectMapper().writeValue(response.getWriter(), e.getMessage());
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
