package internship.mbicycle.storify.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

import java.time.LocalDate;
import java.util.Optional;

import internship.mbicycle.storify.converter.PurchaseConverter;
import internship.mbicycle.storify.dto.PurchaseDTO;
import internship.mbicycle.storify.dto.StorifyUserDTO;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.model.Purchase;
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
    private StorifyUserDTO user;

    @BeforeEach
    void setUp() {
        purchase = Purchase.builder()
            .id(87L)
            .delivered(false)
            .purchaseDate(LocalDate.now())
            .build();

        purchaseDTO = PurchaseDTO.builder()
            .id(87L)
            .delivered(false)
            .purchaseDate(LocalDate.now())
            .build();

        user = new StorifyUserDTO();
        user.setName("Vasili");
        user.setPassword("123456");
        user.setEmail("vasili@gmail.com");
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
        given(purchaseConverter.convertPurchaseDTOToPurchase(purchaseDTO)).willReturn(purchase);
        given(generatorUniqueCode.generationUniqueCode()).willReturn("Code");
        given(purchaseRepository.save(purchase)).willReturn(purchase);
        given(purchaseConverter.convertPurchaseToPurchaseDTO(purchase)).willReturn(purchaseDTO);
        assertEquals(purchaseDTO, purchaseService.savePurchase(user, purchaseDTO));
        then(purchaseRepository).should(only()).save(purchase);
        then(generatorUniqueCode).should(only()).generationUniqueCode();
    }
}
