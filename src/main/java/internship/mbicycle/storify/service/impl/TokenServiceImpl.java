package internship.mbicycle.storify.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import internship.mbicycle.storify.configuration.properties.SecurityProperties;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.service.StorifyUserService;
import internship.mbicycle.storify.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static internship.mbicycle.storify.util.Constants.BEARER;
import static internship.mbicycle.storify.util.TimeConstant.THIRTY_MINUTES;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final SecurityProperties securityProperties;
    private final StorifyUserService userService;

    @Override
    public String createJwtToken(StorifyUser storifyUser) {
        StorifyUser userFromDb = userService.getUserByEmail(storifyUser.getEmail());
        return JWT.create()
                .withSubject(userFromDb.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + THIRTY_MINUTES))
                .withClaim("role", userFromDb.getRole())
                .sign(getEncryptionAlgorithm());
    }

    @Override
    public StorifyUser getUserByJwtToken(String token, String userAgent, HttpServletResponse response) {
        try {
            String email = getEmailByToken(token);
            return userService.getUserByEmail(email);
        } catch (JWTVerificationException e) {
            StorifyUser storifyUser = userService.getStorifyUserByTokenAndUserAgent(token, userAgent);
            storifyUser.getTokenMap().put(userAgent, createJwtToken(storifyUser));
            String jwtToken = createJwtToken(storifyUser);
            userService.saveJwtToken(storifyUser, jwtToken, userAgent);
            response.setHeader(AUTHORIZATION, Strings.concat(BEARER, jwtToken));
            return storifyUser;
        }
    }

    @Override
    public void setTokenPairAfterActivation(StorifyUser storifyUser, String userAgent, HttpServletResponse response) {
        String jwtToken = createJwtToken(storifyUser);
        userService.saveJwtToken(storifyUser, jwtToken, userAgent);
        response.setHeader(AUTHORIZATION, Strings.concat(BEARER, jwtToken));
    }

    private String getEmailByToken(String token) {
        JWTVerifier verifier = JWT.require(getEncryptionAlgorithm()).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

    private Algorithm getEncryptionAlgorithm() {
        return Algorithm.HMAC256(securityProperties.getJwtSecret().getBytes());
    }
}
