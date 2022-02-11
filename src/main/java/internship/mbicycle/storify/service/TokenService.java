package internship.mbicycle.storify.service;

import internship.mbicycle.storify.model.StorifyUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface TokenService {

    String createAccessToken(StorifyUser storifyUser);

    String createRefreshToken(StorifyUser storifyUser);

    UsernamePasswordAuthenticationToken getAuthenticationToken(String authorizationHeader);

    StorifyUser getTokensFromRefreshToken(String authorizationHeader);

    void saveTokens(StorifyUser user, String accessToken, String refreshToken);

    void setTokensAfterActivation(StorifyUser storifyUser);
}
