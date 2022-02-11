package internship.mbicycle.storify.service;

import internship.mbicycle.storify.model.StorifyUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

public interface TokenService {

    String createAccessToken(User user);

    String createRefreshToken(User user);

    UsernamePasswordAuthenticationToken getAuthenticationToken(String authorizationHeader);

    String getAccessTokenFromRefreshToken(String authorizationHeader);

    void saveToken(User user, String accessToken, String refreshToken);

    void setTokensAfterActivation(StorifyUser storifyUser);
}
