package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.dto.OrderDTO;
import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.dto.ProfileDTO;
import internship.mbicycle.storify.dto.StoreDTO;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.model.Store;
import internship.mbicycle.storify.repository.ProfileRepository;
import internship.mbicycle.storify.repository.StoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;


class StoreServiceImplTest {

    private StoreServiceImpl storeService;
    private ProfileServiceImpl profileService;
    private Store store;
    private StoreDTO storeDTO;


    @BeforeEach
    void setUp() {
        storeService = new StoreServiceImpl(Mockito.mock(StoreRepository.class), Mockito.mock(ProfileRepository.class));

        store = Store.builder()
                .id(10L)
                .storeName("Mila")
                .description("The best shop")
                .address("Gomel")
                .profile(Profile.builder()
                        .id(10L)
                        .name("Veronika")
                        .surname("Grig")
                        .town("Gomel")
                        .address("Gomel")
                        .phone("1111")
                        .build())
                .products(List.of(Product.builder()
                        .id(12L)
                        .productName("potato")
                        .image("ffff.jpg")
                        .description("the best potato")
                        .price(100)
                        .count(2)
                        .storeId(10L)
                        .build()))
                .build();

        storeDTO = StoreDTO.builder()
                .id(10L)
                .storeName("Mila")
                .description("The best shop")
                .address("Gomel")
                .profileDTO(ProfileServiceImpl.convertProfileToProfileDTO(store.getProfile()))
                .build();
    }


    @Test
    void testFindStoresByIncorrectIdAndProfileId() {
        assertThrows(ResourceNotFoundException.class, () -> storeService.findStoresByIdAndProfileId(-7L, 3L));
    }

    @Test
    void testMessageFindStoresByIncorrectIdAndProfileId() {
        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () ->
                storeService.findStoresByIdAndProfileId(-7L, 3L));
        Assertions.assertEquals("Store not found.", thrown.getMessage());
    }

    @Test
    void findStoresByProfileId() {
        List<StoreDTO> list = storeService.findStoresByProfileId(0L);
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    void testConvertStoreToStoreDTO() {
        StoreDTO test = StoreServiceImpl.fromStoreToStoreDTO(store);
        Assertions.assertInstanceOf(StoreDTO.class, test);
    }

    @Test
    void testConvertStoreDTOToStore() {
        Store test = StoreServiceImpl.fromStoreDTOToStore(storeDTO);
        Assertions.assertInstanceOf(Store.class, test);
    }
}
