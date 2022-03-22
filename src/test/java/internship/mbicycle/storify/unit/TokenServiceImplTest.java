package internship.mbicycle.storify.unit;

import internship.mbicycle.storify.configuration.properties.SecurityProperties;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.service.StorifyUserService;
import internship.mbicycle.storify.service.impl.TokenServiceImpl;
import internship.mbicycle.storify.util.Constants;
import internship.mbicycle.storify.util.PropertiesUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TokenServiceImplTest {

    private static final String EMAIL = "test@mail.ru";
    private final String token = PropertiesUtil.getProperty("jwt.token.access");

    @Mock
    private SecurityProperties securityProperties;
    @Mock
    private StorifyUserService userService;

    @InjectMocks
    private TokenServiceImpl tokenService;

    private static final StorifyUser storifyUser = StorifyUser.builder()
            .email(EMAIL)
            .role(Constants.ROLE_USER)
            .build();

    @Test
    void shouldCreateAccessToken() {
        given(securityProperties.getJwtSecret()).willReturn("secret");
        String actual = tokenService.createAccessToken(storifyUser);
        assertNotNull(actual);
        then(securityProperties).should(only()).getJwtSecret();

    }

    @Test
    void shouldCreateRefreshToken() {
        given(securityProperties.getJwtSecret()).willReturn("secret");
        String actual = tokenService.createRefreshToken(storifyUser);
        assertNotNull(actual);
        then(securityProperties).should(only()).getJwtSecret();
    }

    @Test
    void shouldUserByAccessToken() {
        given(securityProperties.getJwtSecret()).willReturn("storify");
        StorifyUser actual = tokenService.getUserByAccessToken(token);
        assertEquals(storifyUser, actual);
        then(securityProperties).should(times(2)).getJwtSecret();
    }
}