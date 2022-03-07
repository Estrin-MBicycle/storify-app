package internship.mbicycle.storify.unit;

import internship.mbicycle.storify.configuration.properties.SecurityProperties;
import internship.mbicycle.storify.exception.TokenNotFoundException;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.model.Token;
import internship.mbicycle.storify.service.StorifyUserService;
import internship.mbicycle.storify.service.impl.TokenServiceImpl;
import internship.mbicycle.storify.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static internship.mbicycle.storify.util.ExceptionMessage.NOT_THE_SAME_TOKENS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TokenServiceImplTest {

    @Mock
    private SecurityProperties securityProperties;
    @Mock
    private StorifyUserService userService;

    @InjectMocks
    private TokenServiceImpl tokenService;

    private static final String EMAIL = "test@mail.ru";

    private StorifyUser storifyUser;

    @BeforeEach
    void setUp() {
        storifyUser = StorifyUser.builder()
                .email(EMAIL)
                .role(Constants.ROLE_USER)
                .token(new Token())
                .build();

    }

    @Test
    void shouldCreateAccessToken() {
        given(userService.getUserByEmail(EMAIL)).willReturn(storifyUser);
        given(securityProperties.getJwtSecret()).willReturn("secret");
        String actual = tokenService.createAccessToken(storifyUser);
        assertNotNull(actual);
        then(userService).should(only()).getUserByEmail(EMAIL);
    }

    @Test
    void shouldCreateRefreshToken() {
        given(securityProperties.getJwtSecret()).willReturn("secret");
        String actual = tokenService.createRefreshToken(storifyUser);
        assertNotNull(actual);
    }

    @Test
    void ShouldGetAuthenticationToken() {
        given(userService.getUserByEmail(EMAIL)).willReturn(storifyUser);
        given(securityProperties.getJwtSecret()).willReturn("secret");
        given(securityProperties.isCheckAccess()).willReturn(true);
        String token = tokenService.createAccessToken(storifyUser);
        storifyUser.getToken().setAccessToken(token);
        String authorizationHeader = "Bearer " + token;
        UsernamePasswordAuthenticationToken actual = tokenService.getAuthenticationToken(authorizationHeader);
        assertNotNull(actual);
        storifyUser.getToken().setAccessToken("token");
        TokenNotFoundException exception = assertThrows(TokenNotFoundException.class,
                () -> tokenService.getAuthenticationToken(authorizationHeader));
        assertEquals(String.format(NOT_THE_SAME_TOKENS, token), exception.getMessage());
        then(securityProperties).should(times(2)).isCheckAccess();
        then(securityProperties).should(times(3)).getJwtSecret();
    }

    @Test
    void shouldGetStorifyUserByRefreshToken() {
        given(userService.getUserByEmail(EMAIL)).willReturn(storifyUser);
        given(securityProperties.getJwtSecret()).willReturn("secret");
        String token = tokenService.createRefreshToken(storifyUser);
        storifyUser.getToken().setAccessToken(null);
        storifyUser.getToken().setRefreshToken(token);
        StorifyUser actual = tokenService.getStorifyUserByRefreshToken(token);
        assertNotNull(actual.getToken().getAccessToken());

        storifyUser.getToken().setRefreshToken("token");
        TokenNotFoundException exception = assertThrows(TokenNotFoundException.class,
                () -> tokenService.getStorifyUserByRefreshToken(token));
        assertEquals(String.format(NOT_THE_SAME_TOKENS, token), exception.getMessage());
        then(userService).should(times(3)).getUserByEmail(EMAIL);
    }
}