package internship.mbicycle.storify.configuration.security.parser;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class JwtParser {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    public Optional<String> parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(BEARER)) {
            return Optional.of(headerAuth.substring(BEARER.length()));
        }
        return Optional.empty();
    }
}