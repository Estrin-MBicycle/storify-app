package internship.mbicycle.storify.integration;

import internship.mbicycle.storify.TestMariaDbContainer;
import internship.mbicycle.storify.dto.ProfileDTO;
import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.repository.ProfileRepository;
import internship.mbicycle.storify.repository.StorifyUserRepository;
import internship.mbicycle.storify.service.TokenService;
import internship.mbicycle.storify.util.Constants;
import internship.mbicycle.storify.util.FileReaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestMariaDbContainer
public class ProfileControllerTest {

    private static final String EMAIL = "test@mail.ru";
    private static final String PASSWORD = "123456";
    private static final String CODE = "randomCode";

    private String header;
    private Profile profile;
    private StorifyUser principalUser;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private TokenService tokenService;

    @MockBean
    private StorifyUserRepository userRepository;

    @MockBean
    private ProfileRepository profileRepository;

    @BeforeEach
    void setUp() {
        profile = Profile.builder()
                .id(1L)
                .name("name")
                .surname("surname")
                .town("town")
                .address("address")
                .phone("phone").build();
        principalUser = StorifyUser.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .role(Constants.ROLE_USER)
                .tempConfirmCode(CODE)
                .profile(profile)
                .build();

        given(userRepository.findByEmail(EMAIL)).willReturn(Optional.of(principalUser));

        String token = tokenService.createAccessToken(principalUser);
        header = "Bearer " + token;
        Authentication auth = new UsernamePasswordAuthenticationToken(
                principalUser.getEmail(),
                principalUser.getPassword(),
                principalUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void testGetByEmail() {
        given(userRepository.findByEmail(EMAIL)).willReturn(Optional.of(principalUser));

        webTestClient.get()
                .uri("/profile")
                .header("Authorization", header)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void testUpdateProfile() {
        given(userRepository.findByEmail(EMAIL)).willReturn(Optional.of(principalUser));
        given(profileRepository.save(principalUser.getProfile())).willReturn(profile);

        ProfileDTO profileDTO = ProfileDTO.builder()
                .id(1L)
                .name("name")
                .surname("surname")
                .town("town")
                .address("address").build();

        webTestClient.put()
                .uri("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(profileDTO)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }
}
