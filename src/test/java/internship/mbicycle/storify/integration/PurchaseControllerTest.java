package internship.mbicycle.storify.integration;


import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import internship.mbicycle.storify.TestMariaDbContainer;
import internship.mbicycle.storify.model.Purchase;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.model.Token;
import internship.mbicycle.storify.repository.PurchaseRepository;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestMariaDbContainer
class PurchaseControllerTest {

    private static final String EMAIL = "test@gmail.com";
    private static final String PASSWORD = "12345678";
    private static final String CODE = "randomCode";

    private String header;
    private Purchase purchase;
    private List<Purchase> purchaseList = new ArrayList<>();

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private TokenService tokenService;

    @MockBean
    private StorifyUserRepository userRepository;

    @MockBean
    private PurchaseRepository purchaseRepository;

    @BeforeEach
    void setUp() {
        purchase = Purchase.builder()
            .id(55L)
            .price(5555)
            .uniqueCode("uniqueCode")
            .delivered(true)
            .profileId(69L)
            .purchaseDate(LocalDate.now())
            .build();

        purchaseList = List.of(purchase);

        StorifyUser principalUser = StorifyUser.builder()
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
    void testGetPurchaseByUniqueCode() {
        final String code = "uniqueCode";
        given(purchaseRepository.findPurchaseByUniqueCode(code)).willReturn(Optional.of(purchase));
        webTestClient.get()
            .uri("/purchases/" + code)
            .header("Authorization", header)
            .exchange()
            .expectStatus()
            .is2xxSuccessful();
    }

    @Test
    void testGetPurchaseProfileIdAndIsDelivered() {
        final boolean isDelivered = true;
        final long profileId = 69L;
        given(purchaseRepository.findAllByProfileIdAndDelivered(profileId, isDelivered))
            .willReturn(purchaseList);
        webTestClient.get()
            .uri("/purchases/" + profileId + "/" + isDelivered)
            .header("Authorization", header)
            .exchange()
            .expectStatus()
            .is2xxSuccessful();
    }
}
