package internship.mbicycle.storify.unit;


import internship.mbicycle.storify.converter.StoreConverter;
import internship.mbicycle.storify.dto.StoreDTO;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.model.Store;
import internship.mbicycle.storify.repository.ProfileRepository;
import internship.mbicycle.storify.repository.StoreRepository;
import internship.mbicycle.storify.service.impl.StoreServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
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
    public static final Long LIMIT = 1L;

    @Mock
    private StoreRepository storeRepository;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private StoreConverter storeConverter;

    @InjectMocks
    private StoreServiceImpl storeService;

    @Test
    void shouldFindStoresByProfileId() {
        given(storeRepository.findStoresByProfileId(PROFILE_ID)).willReturn(new ArrayList<>());
        List<StoreDTO> expected = new ArrayList<>();
        List<StoreDTO> actual = storeService.findStoresByProfileId(PROFILE_ID);
        assertEquals(expected, actual);
        then(storeRepository).should(only()).findStoresByProfileId(PROFILE_ID);
        then(profileRepository).shouldHaveNoInteractions();
    }

    @Test
    void shouldFindStoresByProfileIdNot() {
        given(storeRepository.findStoresByProfileIdNot(PROFILE_ID)).willReturn(new ArrayList<>());
        List<StoreDTO> expected = new ArrayList<>();
        List<StoreDTO> actual = storeService.findStoresByProfileIdNot(PROFILE_ID);
        assertEquals(expected, actual);
        then(storeRepository).should(only()).findStoresByProfileIdNot(PROFILE_ID);
        then(profileRepository).shouldHaveNoInteractions();
    }

    @Test
    void shouldFindStoreByIdAndProfileId() {
        given(storeRepository.findStoreByIdAndProfileId(ID, PROFILE_ID)).willReturn(Optional.empty());

        final ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> storeService.findStoreByIdAndProfileId(ID, PROFILE_ID));

        assertEquals(String.format(NOT_FOUND_STORE, ID), exception.getMessage());

        then(storeRepository).should(only()).findStoreByIdAndProfileId(ID, PROFILE_ID);
        then(profileRepository).shouldHaveNoInteractions();
    }

    @Test
    void shouldFindStoreById() {
        given(storeRepository.findStoreById(ID)).willReturn(Optional.empty());

        final ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> storeService.findStoreById(ID));

        assertEquals(String.format(NOT_FOUND_STORE, ID), exception.getMessage());

        then(storeRepository).should(only()).findStoreById(ID);
        then(profileRepository).shouldHaveNoInteractions();
    }

    @Test
    void shouldSaveStore() {
        final Profile profile = Profile.builder().id(PROFILE_ID).build();
        final Store store = Store.builder().id(ID).build();
        final StoreDTO expected = StoreDTO.builder().id(ID).build();


        given(storeConverter.fromStoreToStoreDTO(store)).willReturn(expected);
        given(storeConverter.fromStoreDTOToStore(expected)).willReturn(store);
        given(profileRepository.getById(PROFILE_ID)).willReturn(profile);
        given(storeRepository.save(store)).willReturn(store);

        final StoreDTO actual = storeService.saveStore(expected, PROFILE_ID);
        assertEquals(expected, actual);

        then(storeRepository).should(only()).save(store);
        then(profileRepository).should(only()).getById(PROFILE_ID);
        then(storeConverter).should(times(1)).fromStoreDTOToStore(expected);
        then(storeConverter).should(times(1)).fromStoreToStoreDTO(store);
        then(storeConverter).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldUpdateStore() {
        final Profile profile = Profile.builder().id(PROFILE_ID).build();
        final Store store = Store.builder().id(ID).profile(profile).build();
        final StoreDTO expected = StoreDTO.builder().id(ID).build();

        given(storeRepository.findStoreByIdAndProfileId(ID, PROFILE_ID)).willReturn(Optional.ofNullable(store));
        given(profileRepository.getById(PROFILE_ID)).willReturn(profile);
        assert store != null;
        given(storeConverter.fromStoreToStoreDTO(store)).willReturn(expected);
        given(storeRepository.save(store)).willReturn(store);


        final StoreDTO actual = storeService.updateStore(expected, ID, PROFILE_ID);
        assertEquals(expected, actual);

        then(storeRepository).should(times(1)).findStoreByIdAndProfileId(ID, PROFILE_ID);
        then(storeRepository).should(times(1)).save(store);
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
}