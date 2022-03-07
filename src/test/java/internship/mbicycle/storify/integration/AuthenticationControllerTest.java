package internship.mbicycle.storify.integration;

import internship.mbicycle.storify.TestMariaDbContainer;
import internship.mbicycle.storify.configuration.properties.SecurityProperties;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.model.Token;
import internship.mbicycle.storify.repository.StorifyUserRepository;
import internship.mbicycle.storify.service.TokenService;
import internship.mbicycle.storify.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestMariaDbContainer
class AuthenticationControllerTest {

    private static final String EMAIL = "test@mail.ru";
    private static final String PASSWORD = "123456";
    private static final String CODE = "randomCode";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private TokenService tokenService;

    @MockBean
    private StorifyUserRepository userRepository;

    private StorifyUser storifyUser;

    @BeforeEach
    void setUp() {
        storifyUser = StorifyUser.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .role(Constants.ROLE_USER)
                .token(new Token())
                .tempConfirmCode(CODE)
                .build();
    }

    @Test
    void refreshAccessToken() {
        given(userRepository.findByEmail(EMAIL)).willReturn(Optional.of(storifyUser));
        given(userRepository.save(any())).willReturn(storifyUser);
        String refreshToken = tokenService.createRefreshToken(storifyUser);
        storifyUser.getToken().setRefreshToken(refreshToken);
        String header = "Bearer " + storifyUser.getToken().getRefreshToken();
        webTestClient.get()
                .uri("/token/refresh")
                .header("Authorization", header)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .exists("access_token")
                .expectHeader()
                .exists("refresh_token");
    }
}