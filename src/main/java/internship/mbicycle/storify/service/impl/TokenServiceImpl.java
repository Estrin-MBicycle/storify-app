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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
@RequiredArgsConstructor
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
    public UsernamePasswordAuthenticationToken getAuthenticationTokenFromToken(String token) {
        JWTVerifier verifier = JWT.require(getEncryptionAlgorithm()).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String email = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim("role").asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        return new UsernamePasswordAuthenticationToken(email, null, authorities);
    }

    @Override
    public String getAccessTokenFromRefreshToken(String authorizationHeader) {
        if (authorizationHeader.startsWith("Bearer ")) {
            String refreshToken = authorizationHeader.substring("Bearer ".length());
            JWTVerifier verifier = JWT.require(getEncryptionAlgorithm()).build();
            DecodedJWT decodedJWT = verifier.verify(refreshToken);
            String email = decodedJWT.getSubject();
            StorifyUser storifyUser = userService.getUserByEmail(email);
            return createAccessToken(userService.convertStorifyUserIntoUserDetails(storifyUser));
        }
        return null;
    }

    private Algorithm getEncryptionAlgorithm() {
        return Algorithm.HMAC256(securityProperties.getJwtSecret().getBytes());
    }
}
