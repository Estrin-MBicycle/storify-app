package internship.mbicycle.storify.integration;

import internship.mbicycle.storify.TestMariaDbContainer;
import internship.mbicycle.storify.model.Cart;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.model.Token;
import internship.mbicycle.storify.repository.CartRepository;
import internship.mbicycle.storify.repository.StorifyUserRepository;
import internship.mbicycle.storify.service.TokenService;
import internship.mbicycle.storify.util.Constants;
import internship.mbicycle.storify.util.FileReaderUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Optional;

import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestMariaDbContainer
class StorifyUserControllerTest {

    private static final String EMAIL = "test@mail.ru";
    private static final String PASSWORD = "123456";
    private static final String CODE = "randomCode";

    private String header;
    private StorifyUser principalUser;

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CartRepository cartRepository;

    @Autowired
    private TokenService tokenService;


    @MockBean
    private StorifyUserRepository userRepository;

    @BeforeEach
    void setUp() {
        principalUser = StorifyUser.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .role(Constants.ROLE_USER)
                .token(new Token())
                .tempConfirmCode(CODE)
                .build();
        given(userRepository.findByEmail(EMAIL)).willReturn(Optional.of(principalUser));
        String token = tokenService.createAccessToken(principalUser);
        principalUser.getToken().setAccessToken(token);
        Authentication auth = new UsernamePasswordAuthenticationToken(
                principalUser.getEmail(),
                principalUser.getPassword(),
                principalUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        header = "Bearer " + principalUser.getToken().getAccessToken();
    }

    @Test
    void addNewUser() throws Exception {
        StorifyUser storifyUser = StorifyUser.builder().build();
        given(cartRepository.save(Mockito.any())).willReturn(new Cart());
        given(userRepository.save(Mockito.any())).willReturn(storifyUser);
        String userAsString = FileReaderUtil.readFromJson("json/StorifyUserDTO.json");
        webTestClient.post()
                .uri("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userAsString)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void activateEmail() {
        given(userRepository.findByActivationCode(CODE)).willReturn(Optional.of(principalUser));
        webTestClient.get()
                .uri("/activate/" + CODE)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void sendConfirmationEmail() {
        webTestClient.get()
                .uri("/update/email")
                .header("Authorization", header)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @SneakyThrows
    @Test
    void updateEmail() {
        String newEmailDTOAsString = FileReaderUtil.readFromJson("json/NewEmailDTO.json");
        webTestClient.patch()
                .uri("/update/" + CODE)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newEmailDTOAsString)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }
}
