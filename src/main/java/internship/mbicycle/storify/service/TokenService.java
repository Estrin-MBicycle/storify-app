package internship.mbicycle.storify.service;

import internship.mbicycle.storify.model.StorifyUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface TokenService {

    String createAccessToken(StorifyUser storifyUser);

    String createRefreshToken(StorifyUser storifyUser);

    UsernamePasswordAuthenticationToken getAuthenticationToken(String authorizationHeader);

    StorifyUser getStorifyUserByRefreshToken(String refreshToken);

    void saveTokenPair(StorifyUser user, String accessToken, String refreshToken);

    void setTokenPairAfterActivation(StorifyUser storifyUser);

    String parseTokenByAuthorizationHeader(String authorizationHeader);
}
