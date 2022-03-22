package internship.mbicycle.storify.configuration.security.parser;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static internship.mbicycle.storify.util.Constants.BEARER;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class JwtParser {

    public Optional<String> parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(BEARER)) {
            return Optional.of(headerAuth.substring(BEARER.length()));
        }
        return Optional.empty();
    }
}