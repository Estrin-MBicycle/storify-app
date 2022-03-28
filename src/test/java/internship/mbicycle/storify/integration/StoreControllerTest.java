package internship.mbicycle.storify.integration;

import internship.mbicycle.storify.TestMariaDbContainer;
import internship.mbicycle.storify.converter.StoreConverter;
import internship.mbicycle.storify.dto.StoreDTO;
import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.model.Store;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.repository.ProfileRepository;
import internship.mbicycle.storify.repository.StoreRepository;
import internship.mbicycle.storify.repository.StorifyUserRepository;
import internship.mbicycle.storify.service.TokenService;
import internship.mbicycle.storify.util.Constants;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestMariaDbContainer
public class StoreControllerTest {

    private static final String EMAIL = "test@mail.ru";
    private static final String PASSWORD = "123456";
    private static final String CODE = "randomCode";

    private String header;
    private Store store;
    private Profile profile;
    private StorifyUser principalUser;
    private List<Store> storeList = new ArrayList<>();

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private TokenService tokenService;

    @MockBean
    private StorifyUserRepository userRepository;

    @MockBean
    private StoreRepository storeRepository;

    @MockBean
    private ProfileRepository profileRepository;

    @MockBean
    private StoreConverter storeConverter;

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
        store = Store.builder()
                .id(1L)
                .storeName("store_name")
                .description("description")
                .address("address")
                .profile(profile).build();

        storeList = List.of(store);

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
    void testGetStoresByProfile() {
        given(storeRepository.findStoresByProfileId(1L)).willReturn(storeList);
        given(userRepository.findByEmail(EMAIL)).willReturn(Optional.of(principalUser));

        webTestClient.get()
                .uri("/stores/my")
                .header("Authorization", header)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void testGetStores() {
        given(storeRepository.findAll()).willReturn(storeList);

        webTestClient.get()
                .uri("/stores")
                .header("Authorization", header)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void testGetStore() {
        given(storeRepository.findStoreById(1L)).willReturn(Optional.of(store));

        webTestClient.get()
                .uri("/stores/1")
                .header("Authorization", header)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void testSaveStore() {
        StoreDTO storeDTO = StoreDTO.builder()
                .id(2L)
                .storeName("store_name1")
                .description("description")
                .address("address")
                .build();

        given(userRepository.findByEmail(EMAIL)).willReturn(Optional.of(principalUser));
        given(profileRepository.getById(1L)).willReturn(profile);
        given(storeRepository.findStoreByStoreName("store_name1")).willReturn(Optional.empty());
        given(storeRepository.save(store)).willReturn(store);
        given(storeConverter.fromStoreToStoreDTO(store)).willReturn(storeDTO);
        given(storeConverter.fromStoreDTOToStore(storeDTO)).willReturn(store);

        webTestClient.post()
                .uri("/stores")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(storeDTO)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void testUpdateStore() {
        given(userRepository.findByEmail(EMAIL)).willReturn(Optional.of(principalUser));
        given(profileRepository.getById(1L)).willReturn(profile);
        given(storeRepository.findStoreByIdAndProfileId(1L, 1L)).willReturn(Optional.of(store));
        given(storeRepository.save(store)).willReturn(store);

        StoreDTO storeDTO = StoreDTO.builder()
                .id(1L)
                .storeName("store_name")
                .description("description")
                .address("address")
                .build();

        webTestClient.put()
                .uri("/stores/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(storeDTO)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void testDeleteStore() {
        given(userRepository.findByEmail(EMAIL)).willReturn(Optional.of(principalUser));
        given(storeRepository.findStoreById(1L)).willReturn(Optional.of(store));

        webTestClient.delete()
                .uri("/stores/1")
                .header("Authorization", header)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void testFindMostPurchasedProductsInStore() {
        given(storeRepository.findMostPurchasedProductsInStore(1L, 1L)).willReturn(new ArrayList<>());

        webTestClient.get()
                .uri("/stores/products/most/1/1")
                .header("Authorization", header)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void testFindLestPurchasedProductsInStore() {
        given(storeRepository.findLestPurchasedProductsInStore(1L, 1L)).willReturn(new ArrayList<>());

        webTestClient.get()
                .uri("/stores/products/lest/1/1")
                .header("Authorization", header)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void testGetIncome() {
        given(storeRepository.getIncomeForAllTime(1L)).willReturn(10);
        given(storeRepository.getIncomeForMonths(1L, 12)).willReturn(20);
        given(storeRepository.getIncomeForMonths(1L, 6)).willReturn(30);
        given(storeRepository.getIncomeForMonths(1L, 1)).willReturn(40);
        given(storeRepository.getIncomeForDay(1L)).willReturn(50);

        webTestClient.get()
                .uri("/stores/income/1")
                .header("Authorization", header)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void testGetIncomeForPeriod() {
        given(storeRepository.getIncomeForAllTime(1L)).willReturn(10);
        given(storeRepository.getIncomeForMonths(1L, 12)).willReturn(20);
        given(storeRepository.getIncomeForMonths(1L, 6)).willReturn(30);
        given(storeRepository.getIncomeForMonths(1L, 1)).willReturn(40);
        given(storeRepository.getIncomeForDay(1L)).willReturn(50);

        webTestClient.get()
                .uri("/stores/income/ALL_TIME/1")
                .header("Authorization", header)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }
}
