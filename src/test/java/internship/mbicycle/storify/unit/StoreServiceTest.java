package internship.mbicycle.storify.unit;


import internship.mbicycle.storify.converter.StoreConverter;
import internship.mbicycle.storify.dto.IncomePeriodDTO;
import internship.mbicycle.storify.dto.StoreDTO;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.model.Store;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.repository.ProfileRepository;
import internship.mbicycle.storify.repository.StoreRepository;
import internship.mbicycle.storify.service.StorifyUserService;
import internship.mbicycle.storify.service.impl.StoreServiceImpl;
import internship.mbicycle.storify.util.IncomePeriod;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static internship.mbicycle.storify.util.ExceptionMessage.NOT_FOUND_STORE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    public static final Long ID = 2L;
    public static final Long PROFILE_ID = 1L;
    public static final String PROFILE_EMAIL = "m@mail.com";
    public static final Long LIMIT = 1L;

    @Mock
    private StoreRepository storeRepository;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private StoreConverter storeConverter;
    @Mock
    private StorifyUserService userService;

    @InjectMocks
    private StoreServiceImpl storeService;

    @Test
    void shouldGetStoresByEmail() {
        final List<StoreDTO> expected = new ArrayList<>();
        final Profile profile = Profile.builder().id(PROFILE_ID).build();
        final StorifyUser storifyUser = StorifyUser.builder().id(1L).profile(profile).build();

        given(storeRepository.findStoresByProfileId(PROFILE_ID)).willReturn(new ArrayList<>());
        given(userService.getUserByEmail(PROFILE_EMAIL)).willReturn(storifyUser);

        final List<StoreDTO> actual = storeService.getStoresByUserEmail(PROFILE_EMAIL);
        assertEquals(expected, actual);

        then(storeRepository).should(only()).findStoresByProfileId(PROFILE_ID);
        then(userService).should(only()).getUserByEmail(PROFILE_EMAIL);
        then(profileRepository).shouldHaveNoInteractions();
        then(storeConverter).shouldHaveNoInteractions();
    }

    @Test
    void shouldGetStores() {
        final List<StoreDTO> expected = new ArrayList<>();

        given(storeRepository.findAll()).willReturn(new ArrayList<>());

        final List<StoreDTO> actual = storeService.getStores();
        assertEquals(expected, actual);

        then(storeRepository).should(only()).findAll();
        then(userService).shouldHaveNoInteractions();
        then(profileRepository).shouldHaveNoInteractions();
    }

    @Test
    void shouldFindStoreById() {
        given(storeRepository.findStoreById(ID)).willReturn(Optional.empty());

        final ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> storeService.getStoreById(ID));

        assertEquals(String.format(NOT_FOUND_STORE, ID), exception.getMessage());

        then(storeRepository).should(only()).findStoreById(ID);
        then(profileRepository).shouldHaveNoInteractions();
    }

    @Test
    void shouldSaveStore() {
        final Profile profile = Profile.builder().id(PROFILE_ID).build();
        final StorifyUser storifyUser = StorifyUser.builder().id(1L).profile(profile).build();
        final Store store = Store.builder().id(ID).storeName("storeName").build();
        final StoreDTO expected = StoreDTO.builder().id(ID).storeName("storeName").build();

        given(userService.getUserByEmail(PROFILE_EMAIL)).willReturn(storifyUser);
        given(storeConverter.fromStoreToStoreDTO(store)).willReturn(expected);
        given(storeConverter.fromStoreDTOToStore(expected)).willReturn(store);
        given(profileRepository.getById(PROFILE_ID)).willReturn(profile);
        given(storeRepository.save(store)).willReturn(store);
        given(storeRepository.findStoreByStoreName("storeName")).willReturn(Optional.empty());

        final StoreDTO actual = storeService.saveStore(expected, PROFILE_EMAIL);
        assertEquals(expected, actual);

        then(storeRepository).should(times(1)).save(store);
        then(storeRepository).should(times(1)).findStoreByStoreName("storeName");
        then(storeRepository).shouldHaveNoMoreInteractions();
        then(userService).should(only()).getUserByEmail(PROFILE_EMAIL);
        then(profileRepository).should(only()).getById(PROFILE_ID);
        then(storeConverter).should(times(1)).fromStoreDTOToStore(expected);
        then(storeConverter).should(times(1)).fromStoreToStoreDTO(store);
        then(storeConverter).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldUpdateStore() {
        final Profile profile = Profile.builder().id(PROFILE_ID).build();
        final StorifyUser storifyUser = StorifyUser.builder().id(1L).profile(profile).build();
        final Store store = Store.builder().id(ID).profile(profile).build();
        final StoreDTO expected = StoreDTO.builder().id(ID).build();

        given(userService.getUserByEmail(PROFILE_EMAIL)).willReturn(storifyUser);
        given(storeRepository.findStoreByIdAndProfileId(ID, PROFILE_ID)).willReturn(Optional.ofNullable(store));
        given(profileRepository.getById(PROFILE_ID)).willReturn(profile);
        assert store != null;
        given(storeConverter.fromStoreToStoreDTO(store)).willReturn(expected);
        given(storeRepository.save(store)).willReturn(store);

        final StoreDTO actual = storeService.updateStore(expected, ID, PROFILE_EMAIL);
        assertEquals(expected, actual);

        then(storeRepository).should(times(1)).findStoreByIdAndProfileId(ID, PROFILE_ID);
        then(storeRepository).should(times(1)).save(store);
        then(userService).should(only()).getUserByEmail(PROFILE_EMAIL);
        then(storeRepository).shouldHaveNoMoreInteractions();
        then(profileRepository).should(only()).getById(PROFILE_ID);
        then(storeConverter).should(only()).fromStoreToStoreDTO(store);
    }

    @Test
    void shouldFindMostPurchasedProductsInStore() {
        given(storeRepository.findMostPurchasedProductsInStore(ID, LIMIT)).willReturn(new ArrayList<>());
        List<StoreRepository.PurchasedAndNotPaidProduct> expected = new ArrayList<>();
        List<StoreRepository.PurchasedAndNotPaidProduct> actual = storeService.findMostPurchasedProductsInStore(ID, LIMIT);
        assertEquals(expected, actual);
        then(storeRepository).should(only()).findMostPurchasedProductsInStore(ID, LIMIT);
    }

    @Test
    void shouldFindLestPurchasedProductsInStore() {
        given(storeRepository.findLestPurchasedProductsInStore(ID, LIMIT)).willReturn(new ArrayList<>());
        List<StoreRepository.PurchasedAndNotPaidProduct> expected = new ArrayList<>();
        List<StoreRepository.PurchasedAndNotPaidProduct> actual = storeService.findLestPurchasedProductsInStore(ID, LIMIT);
        assertEquals(expected, actual);
        then(storeRepository).should(only()).findLestPurchasedProductsInStore(ID, LIMIT);
    }

    @Test
    void shouldGetIncome() {
        given(storeRepository.getIncomeForAllTime(PROFILE_ID)).willReturn(10);
        given(storeRepository.getIncomeForMonths(PROFILE_ID, 12)).willReturn(20);
        given(storeRepository.getIncomeForMonths(PROFILE_ID, 6)).willReturn(30);
        given(storeRepository.getIncomeForMonths(PROFILE_ID, 1)).willReturn(40);
        given(storeRepository.getIncomeForDay(PROFILE_ID)).willReturn(50);

        Map<IncomePeriod, Integer> map = Map.of(
                IncomePeriod.ALL_TIME, 10,
                IncomePeriod.YEAR, 20,
                IncomePeriod.HALF_YEAR, 30,
                IncomePeriod.LAST_MONTH, 40,
                IncomePeriod.TODAY, 50
        );
        IncomePeriodDTO expected = IncomePeriodDTO.builder().income(map).build();
        IncomePeriodDTO actual = storeService.getIncome(PROFILE_ID);

        assertEquals(expected, actual);

        then(storeRepository).should(times(1)).getIncomeForAllTime(PROFILE_ID);
        then(storeRepository).should(times(1)).getIncomeForMonths(PROFILE_ID, 12);
        then(storeRepository).should(times(1)).getIncomeForMonths(PROFILE_ID, 6);
        then(storeRepository).should(times(1)).getIncomeForMonths(PROFILE_ID, 1);
        then(storeRepository).should(times(1)).getIncomeForDay(PROFILE_ID);
        then(storeRepository).shouldHaveNoMoreInteractions();
        then(profileRepository).shouldHaveNoInteractions();
        then(storeConverter).shouldHaveNoInteractions();
    }

    @Test
    void shouldGetIncomeForPeriod() {
        given(storeRepository.getIncomeForAllTime(PROFILE_ID)).willReturn(10);
        given(storeRepository.getIncomeForMonths(PROFILE_ID, 12)).willReturn(20);
        given(storeRepository.getIncomeForMonths(PROFILE_ID, 6)).willReturn(30);
        given(storeRepository.getIncomeForMonths(PROFILE_ID, 1)).willReturn(40);
        given(storeRepository.getIncomeForDay(PROFILE_ID)).willReturn(50);

        Integer expected = 10;
        Integer actual = storeService.getIncomeForPeriod(IncomePeriod.ALL_TIME, PROFILE_ID);

        assertEquals(expected, actual);

        then(storeRepository).should(times(1)).getIncomeForAllTime(PROFILE_ID);
        then(storeRepository).should(times(1)).getIncomeForMonths(PROFILE_ID, 12);
        then(storeRepository).should(times(1)).getIncomeForMonths(PROFILE_ID, 6);
        then(storeRepository).should(times(1)).getIncomeForMonths(PROFILE_ID, 1);
        then(storeRepository).should(times(1)).getIncomeForDay(PROFILE_ID);
        then(storeRepository).shouldHaveNoMoreInteractions();
        then(profileRepository).shouldHaveNoInteractions();
        then(storeConverter).shouldHaveNoInteractions();
    }
}