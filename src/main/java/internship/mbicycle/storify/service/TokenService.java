package internship.mbicycle.storify.service;

import internship.mbicycle.storify.model.StorifyUser;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {

    String createAccessToken(StorifyUser storifyUser);

    String createRefreshToken(StorifyUser storifyUser);

    StorifyUser getUserByAccessToken(String token);

    StorifyUser getStorifyUserByRefreshToken(HttpServletRequest refreshToken);

    void saveTokenPair(StorifyUser user, String accessToken, String refreshToken);

    void setTokenPairAfterActivation(StorifyUser storifyUser);
}
