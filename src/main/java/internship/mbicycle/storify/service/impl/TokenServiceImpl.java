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
    public String createAccessToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 5)) // 5 minutes
                .withClaim("role", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(getEncryptionAlgorithm());
    }

    @Override
    public String createRefreshToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 10)) // 10 days
                .sign(getEncryptionAlgorithm());
    }

    @Override
    public UsernamePasswordAuthenticationToken getAuthenticationToken(String authorizationHeader) {
        StorifyUser storifyUser = getStorifyUserFromAuthorizationHeader(authorizationHeader);
        User user = userService.convertStorifyUserIntoUserDetails(storifyUser);
        return new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
    }

    //This method doesn't work
    @Override
    public String getAccessTokenFromRefreshToken(String authorizationHeader) {
        StorifyUser storifyUser = getStorifyUserFromAuthorizationHeader(authorizationHeader);
        return createAccessToken(userService.convertStorifyUserIntoUserDetails(storifyUser));
    }

    private StorifyUser getStorifyUserFromAuthorizationHeader(String authorizationHeader) {
        if (authorizationHeader == null) {
            throw new RuntimeException("The token not found");
        }
        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("The token is incorrect");
        }
        String token = authorizationHeader.substring("Bearer ".length());
        JWTVerifier verifier = JWT.require(getEncryptionAlgorithm()).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String email = decodedJWT.getSubject();
        StorifyUser storifyUser = userService.getUserByEmail(email);
        if (securityProperties.isCheckAccess() && !storifyUser.getToken().getAccessToken().equals(token)) {
            throw new RuntimeException("The tokens aren's the same");
        }
        return storifyUser;
    }

    @Override
    public void saveToken(User user, String accessToken, String refreshToken) {
        StorifyUser storifyUser = userService.getUserByEmail(user.getUsername());
        storifyUser.getToken().setAccessToken(accessToken);
        storifyUser.getToken().setRefreshToken(refreshToken);
        log.info("access token from save method {}", storifyUser.getToken().getAccessToken());
        userService.updateStorifyUser(storifyUser);
    }

    @Override
    public void setTokensAfterActivation(StorifyUser storifyUser) {
        User user = userService.convertStorifyUserIntoUserDetails(storifyUser);
        String accessToken = createAccessToken(user);
        String refreshToken = createRefreshToken(user);
        saveToken(user, accessToken, refreshToken);
    }

    private Algorithm getEncryptionAlgorithm() {
        return Algorithm.HMAC256(securityProperties.getJwtSecret().getBytes());
    }
}
