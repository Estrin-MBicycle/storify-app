package internship.mbicycle.storify.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import internship.mbicycle.storify.configuration.properties.SecurityProperties;
import internship.mbicycle.storify.configuration.security.parser.JwtParser;
import internship.mbicycle.storify.exception.InvalidAuthorizationHeaderException;
import internship.mbicycle.storify.exception.TokenNotFoundException;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.service.StorifyUserService;
import internship.mbicycle.storify.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static internship.mbicycle.storify.util.ExceptionMessage.INVALID_AUTHORIZATION_HEADER;
import static internship.mbicycle.storify.util.ExceptionMessage.NOT_THE_SAME_TOKENS;
import static internship.mbicycle.storify.util.TimeConstant.TEN_DAYS;
import static internship.mbicycle.storify.util.TimeConstant.THIRTY_MINUTES;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final SecurityProperties securityProperties;
    private final StorifyUserService userService;
    private final JwtParser jwtParser;

    @Override
    public String createAccessToken(StorifyUser storifyUser) {
        StorifyUser userFromDb = userService.getUserByEmail(storifyUser.getEmail());
        return JWT.create()
                .withSubject(userFromDb.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + THIRTY_MINUTES))
                .withClaim("role", userFromDb.getRole())
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
        StorifyUser storifyUser = getStorifyUserByToken(token);
        if (!isValidAccessToken(storifyUser, token)) {
            throw new TokenNotFoundException(String.format(NOT_THE_SAME_TOKENS, token));
        }
        return storifyUser;
    }

    @Override
    public StorifyUser getStorifyUserByRefreshToken(HttpServletRequest request) {
        String refreshToken = jwtParser.parseJwt(request)
                .orElseThrow(() -> new InvalidAuthorizationHeaderException(INVALID_AUTHORIZATION_HEADER));
        StorifyUser storifyUser = getStorifyUserByToken(refreshToken);
        if (!storifyUser.getToken().getRefreshToken().equals(refreshToken)) {
            throw new TokenNotFoundException(String.format(NOT_THE_SAME_TOKENS, refreshToken));
        }
        storifyUser.getToken().setAccessToken(createAccessToken(storifyUser));
        storifyUser.getToken().setRefreshToken(createRefreshToken(storifyUser));
        userService.updateStorifyUser(storifyUser);
        return storifyUser;
    }

    @Override
    public void saveTokenPair(StorifyUser storifyUser, String accessToken, String refreshToken) {
        storifyUser.getToken().setAccessToken(accessToken);
        storifyUser.getToken().setRefreshToken(refreshToken);
        userService.updateStorifyUser(storifyUser);
    }

    @Override
    public void setTokenPairAfterActivation(StorifyUser storifyUser) {
        String accessToken = createAccessToken(storifyUser);
        String refreshToken = createRefreshToken(storifyUser);
        saveTokenPair(storifyUser, accessToken, refreshToken);
    }

    private boolean isValidAccessToken(StorifyUser storifyUser, String token) {
        if (securityProperties.isCheckAccess()) {
            return storifyUser.getToken().getAccessToken().equals(token);
        } else {
            return true;
        }
    }

    private StorifyUser getStorifyUserByToken(String token) {
        JWTVerifier verifier = JWT.require(getEncryptionAlgorithm()).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String email = decodedJWT.getSubject();
        return userService.getUserByEmail(email);
    }

    private Algorithm getEncryptionAlgorithm() {
        return Algorithm.HMAC256(securityProperties.getJwtSecret().getBytes());
    }
}
