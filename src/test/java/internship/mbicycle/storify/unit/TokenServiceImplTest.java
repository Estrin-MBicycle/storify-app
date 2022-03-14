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
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

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
    void shouldCreateJwtToken() {
        given(userService.getUserByEmail(EMAIL)).willReturn(storifyUser);
        given(securityProperties.getJwtSecret()).willReturn("secret");
        String actual = tokenService.createJwtToken(storifyUser);
        assertNotNull(actual);
        then(userService).should(only()).getUserByEmail(EMAIL);
        then(securityProperties).should(only()).getJwtSecret();

    }

    @Test
    void shouldGetUserByJwtToken() {
        given(userService.getUserByEmail(EMAIL)).willReturn(storifyUser);
        given(securityProperties.getJwtSecret()).willReturn("storify");
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36";
        HttpServletResponse response = new MockHttpServletResponse();
        StorifyUser actual = tokenService.getUserByJwtToken(token, userAgent, response);
        assertEquals(storifyUser, actual);
        then(userService).should(only()).getUserByEmail(EMAIL);
        then(securityProperties).should(times(1)).getJwtSecret();
    }
}