package internship.mbicycle.storify.service;

import internship.mbicycle.storify.model.StorifyUser;

import javax.servlet.http.HttpServletResponse;

public interface TokenService {

    String createJwtToken(StorifyUser storifyUser);

    StorifyUser getUserByJwtToken(String token, String userAgent, HttpServletResponse response);

    void setTokenPairAfterActivation(StorifyUser storifyUser, String userAgent, HttpServletResponse response);
}
