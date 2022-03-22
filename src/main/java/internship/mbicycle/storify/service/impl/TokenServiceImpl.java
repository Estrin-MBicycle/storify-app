package internship.mbicycle.storify.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import internship.mbicycle.storify.configuration.properties.SecurityProperties;
import internship.mbicycle.storify.configuration.security.parser.JwtParser;
import internship.mbicycle.storify.exception.TokenNotFoundException;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.service.StorifyUserService;
import internship.mbicycle.storify.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static internship.mbicycle.storify.util.Constants.BEARER;
import static internship.mbicycle.storify.util.ExceptionMessage.NOT_FOUND_TOKEN;
import static internship.mbicycle.storify.util.ExceptionMessage.NOT_THE_SAME_TOKENS;
import static internship.mbicycle.storify.util.TimeConstant.TEN_DAYS;
import static internship.mbicycle.storify.util.TimeConstant.THIRTY_MINUTES;
import static org.springframework.http.HttpHeaders.USER_AGENT;


@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final SecurityProperties securityProperties;
    private final StorifyUserService userService;
    private final JwtParser jwtParser;

    @Override
    public String createAccessToken(StorifyUser storifyUser) {
        return JWT.create()
                .withSubject(storifyUser.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + THIRTY_MINUTES))
                .withClaim("role", storifyUser.getRole())
                .sign(getEncryptionAlgorithm());
    }

    @Override
    public String createRefreshToken(StorifyUser storifyUser) {
        return JWT.create()
                .withSubject(storifyUser.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + TEN_DAYS))
                .sign(getEncryptionAlgorithm());
    }

    @Override
    public StorifyUser getUserByAccessToken(String token) {
        return StorifyUser.builder()
                .email(getEmailByToken(token))
                .role(getRoleByToken(token))
                .build();
    }

    @Override
    public void setTokenPair(StorifyUser storifyUser, String userAgent, HttpServletResponse response) {
        String accessToken = createAccessToken(storifyUser);
        String refreshToken = createRefreshToken(storifyUser);
        userService.saveRefreshToken(storifyUser, refreshToken, userAgent);
        response.setHeader("access_token", Strings.concat(BEARER, accessToken));
        response.setHeader("refresh_token", Strings.concat(BEARER, refreshToken));
    }

    @Override
    public void setTokenPairByRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        jwtParser.parseJwt(request).ifPresentOrElse(token -> {
            String userAgent = request.getHeader(USER_AGENT);
            StorifyUser user = userService.getStorifyUserByTokenAndUserAgent(token, userAgent);
            if (getEmailByToken(token).equals(user.getEmail())) {
                setTokenPair(user, userAgent, response);
            } else {
                throw new TokenNotFoundException(String.format(NOT_THE_SAME_TOKENS, token));
            }
        }, () -> {
            throw new TokenNotFoundException(NOT_FOUND_TOKEN);
        });
    }

    private String getEmailByToken(String token) {
        JWTVerifier verifier = JWT.require(getEncryptionAlgorithm()).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

    private String getRoleByToken(String token) {
        JWTVerifier verifier = JWT.require(getEncryptionAlgorithm()).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim("role").asString();
    }

    private Algorithm getEncryptionAlgorithm() {
        return Algorithm.HMAC256(securityProperties.getJwtSecret().getBytes());
    }
}
