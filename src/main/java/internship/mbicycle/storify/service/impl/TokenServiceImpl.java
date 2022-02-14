package internship.mbicycle.storify.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import internship.mbicycle.storify.configuration.properties.SecurityProperties;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.service.StorifyUserService;
import internship.mbicycle.storify.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;

import static internship.mbicycle.storify.util.ExceptionMessage.INVALID_TOKEN;
import static internship.mbicycle.storify.util.ExceptionMessage.NOT_THE_SAME_TOKENS;
import static internship.mbicycle.storify.util.TimeConstant.FIVE_MINUTES;
import static internship.mbicycle.storify.util.TimeConstant.TEN_DAYS;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService {

    private final SecurityProperties securityProperties;
    private final StorifyUserService userService;

    @Override
    public String createAccessToken(StorifyUser storifyUser) {
        StorifyUser userFromDb = userService.getUserByEmail(storifyUser.getEmail());
        return JWT.create()
                .withSubject(userFromDb.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + FIVE_MINUTES))
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
    public UsernamePasswordAuthenticationToken getAuthenticationToken(String authorizationHeader) {
        String token = parseTokenByAuthorizationHeader(authorizationHeader);
        StorifyUser storifyUser = getStorifyUserByToken(token);
        if (!isValidAccessToken(storifyUser, token)) {
            throw new RuntimeException(NOT_THE_SAME_TOKENS);
        }
        return new UsernamePasswordAuthenticationToken(storifyUser.getEmail(), null, storifyUser.getAuthorities());
    }

    @Override
    public StorifyUser getStorifyUserByRefreshToken(String refreshToken) {
        StorifyUser storifyUser = getStorifyUserByToken(refreshToken);
        if (!storifyUser.getToken().getRefreshToken().equals(refreshToken)) {
            throw new RuntimeException(NOT_THE_SAME_TOKENS);
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
            if (storifyUser.getToken().getAccessToken().equals(token)) {
                return true;
            }
        }
        return false;
    }

    public String parseTokenByAuthorizationHeader(String authorizationHeader) {
        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException(INVALID_TOKEN);
        }
        return authorizationHeader.substring("Bearer ".length());
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
