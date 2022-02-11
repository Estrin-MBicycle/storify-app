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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

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
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 5)) // 5 minutes
                .withClaim("role", userFromDb.getRole())
                .sign(getEncryptionAlgorithm());
    }

    @Override
    public String createRefreshToken(StorifyUser storifyUser) {
        return JWT.create()
                .withSubject(storifyUser.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 10)) // 10 days
                .sign(getEncryptionAlgorithm());
    }

    @Override
    public UsernamePasswordAuthenticationToken getAuthenticationToken(String authorizationHeader) {
        StorifyUser storifyUser = getStorifyUserFromAuthorizationHeader(authorizationHeader);
        return new UsernamePasswordAuthenticationToken(storifyUser.getEmail(), null, storifyUser.getAuthorities());
    }

    //This method doesn't work
    @Override
    public StorifyUser getTokensFromRefreshToken(String authorizationHeader) {
        String token = parseTokenFromAuthorizationHeader(authorizationHeader);
        JWTVerifier verifier = JWT.require(getEncryptionAlgorithm()).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String email = decodedJWT.getSubject();
        StorifyUser storifyUser = userService.getUserByEmail(email);
        if (!storifyUser.getToken().getRefreshToken().equals(token)) {
            throw new RuntimeException("Tokens aren't the same");
        }
        storifyUser.getToken().setAccessToken(createAccessToken(storifyUser));
        storifyUser.getToken().setRefreshToken(createRefreshToken(storifyUser));
        userService.updateStorifyUser(storifyUser);
        return storifyUser;
    }

    private StorifyUser getStorifyUserFromAuthorizationHeader(String authorizationHeader) {
        String token = parseTokenFromAuthorizationHeader(authorizationHeader);
        JWTVerifier verifier = JWT.require(getEncryptionAlgorithm()).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String email = decodedJWT.getSubject();
        StorifyUser storifyUser = userService.getUserByEmail(email);
        if (securityProperties.isCheckAccess()) {
            if (!storifyUser.getToken().getAccessToken().equals(token)) {
                throw new RuntimeException("Tokens aren't the same");
            }
        }
        return storifyUser;
    }

    private String parseTokenFromAuthorizationHeader(String authorizationHeader) {
        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("The token is incorrect");
        }
        return authorizationHeader.substring("Bearer ".length());
    }

    @Override
    public void saveTokens(StorifyUser storifyUser, String accessToken, String refreshToken) {
        storifyUser.getToken().setAccessToken(accessToken);
        storifyUser.getToken().setRefreshToken(refreshToken);
        userService.updateStorifyUser(storifyUser);
    }

    @Override
    public void setTokensAfterActivation(StorifyUser storifyUser) {
        String accessToken = createAccessToken(storifyUser);
        String refreshToken = createRefreshToken(storifyUser);
        saveTokens(storifyUser, accessToken, refreshToken);
    }

    private Algorithm getEncryptionAlgorithm() {
        return Algorithm.HMAC256(securityProperties.getJwtSecret().getBytes());
    }
}
