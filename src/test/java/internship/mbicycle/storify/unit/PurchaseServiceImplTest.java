package internship.mbicycle.storify.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import internship.mbicycle.storify.converter.PurchaseConverter;
import internship.mbicycle.storify.dto.CartDTO;
import internship.mbicycle.storify.dto.ProductDetailInCartDTO;
import internship.mbicycle.storify.dto.PurchaseDTO;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.model.Purchase;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.repository.PurchaseRepository;
import internship.mbicycle.storify.service.MailService;
import internship.mbicycle.storify.service.ProductService;
import internship.mbicycle.storify.service.impl.GeneratorUniqueCodeImpl;
import internship.mbicycle.storify.service.impl.PurchaseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceImplTest {

    @Mock
    private PurchaseRepository purchaseRepository;
    @Mock
    private GeneratorUniqueCodeImpl generatorUniqueCode;
    @Mock
    private PurchaseConverter purchaseConverter;
    @Mock
    private MailService mailService;
    @Mock
    private ProductService productService;

    @InjectMocks
    private PurchaseServiceImpl purchaseService;
    private Purchase purchase;
    private PurchaseDTO purchaseDTO;
    private StorifyUser user;
    private CartDTO cartDTO;

    @BeforeEach
    void setUp() {
        Profile profile = Profile.builder()
            .id(4L)
            .address("Adress")
            .name("Profile name")
            .phone("100010001")
            .build();

        ProductDetailInCartDTO productDetailInCartDTO = ProductDetailInCartDTO.builder()
            .amount(5)
            .price(1000)
            .productId(5L)
            .name("Name")
            .build();

        cartDTO = CartDTO.builder()
            .sum(5000)
            .productDetailInCartDTOList(List.of(productDetailInCartDTO))
            .build();

        purchase = Purchase.builder()
            .id(null)
            .delivered(false)
            .profileId(4L)
            .price(5000)
            .uniqueCode("Code")
            .purchaseDate(LocalDate.now())
            .products(Map.of(5L, 5))
            .build();

        purchaseDTO = PurchaseDTO.builder()
            .id(87L)
            .delivered(false)
            .purchaseDate(LocalDate.now())
            .profileId(4L)
            .price(5000)
            .productDTOMap(Map.of(5L, 5))
            .build();

        user = new StorifyUser();
        user.setName("Vasili");
        user.setPassword("123456");
        user.setEmail("vasili@gmail.com");
        user.setProfile(profile);
    }

    @Test
    void testFindByIncorrectId() {
        final Long id = 5L;
        given(purchaseRepository.findById(id)).willReturn(Optional.empty());
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
            purchaseService.getPurchaseById(id));
        assertEquals("Purchase not found.", thrown.getMessage());
    }

    @Test
    void testFindById() {
        final Long id = 89L;
        given(purchaseRepository.findById(id)).willReturn(Optional.of(purchase));
        given(purchaseConverter.convertPurchaseToPurchaseDTO(purchase)).willReturn(purchaseDTO);
        PurchaseDTO actual = purchaseService.getPurchaseById(id);
        assertEquals(purchaseDTO, actual);
    }

    @Test
    void testSavePurchase() {
        given(generatorUniqueCode.generationUniqueCode()).willReturn("Code");
        given(purchaseRepository.save(purchase)).willReturn(purchase);
        given(purchaseConverter.convertPurchaseToPurchaseDTO(purchase)).willReturn(purchaseDTO);
        assertEquals(purchaseDTO, purchaseService.savePurchase(user, cartDTO));
        then(purchaseRepository).should(only()).save(purchase);
        then(generatorUniqueCode).should(only()).generationUniqueCode();
    }
}
