package internship.mbicycle.storify.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

public interface TokenService {

    String createAccessToken(User user);

    String createRefreshToken(User user);

    UsernamePasswordAuthenticationToken getAuthenticationTokenFromToken(String token);

    String getAccessTokenFromRefreshToken(String authorizationHeader);
}
