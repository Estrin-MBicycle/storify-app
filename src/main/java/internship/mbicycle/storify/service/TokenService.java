package internship.mbicycle.storify.service;

import internship.mbicycle.storify.model.StorifyUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TokenService {

    String createAccessToken(StorifyUser storifyUser);

    String createRefreshToken(StorifyUser storifyUser);

    StorifyUser getUserByAccessToken(String token);

    void setTokenPair(StorifyUser storifyUser, String userAgent, HttpServletResponse response);

    void setTokenPairByRefreshToken(HttpServletRequest request, HttpServletResponse response);
}
