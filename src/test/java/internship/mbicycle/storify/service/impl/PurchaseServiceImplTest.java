package internship.mbicycle.storify.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

import java.time.LocalDate;
import java.util.Optional;

import internship.mbicycle.storify.dto.PurchaseDTO;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.model.Purchase;
import internship.mbicycle.storify.repository.PurchaseRepository;
import org.junit.jupiter.api.Assertions;
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

    @InjectMocks
    private PurchaseServiceImpl purchaseService;
    private Purchase purchase;
    private PurchaseDTO purchaseDTO;

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
        PurchaseDTO actual = purchaseService.getPurchaseById(id);
        assertEquals(purchaseDTO, actual);
    }

    @Test
    void testSaveProduct() {
        purchaseService.savePurchase(purchaseDTO);
        then(purchaseRepository).should(only()).save(purchase);
        then(generatorUniqueCode).should(only()).generationUniqueCode();
    }

    @Test
    void testConvertPurchaseToPurchaseDTO() {
        PurchaseDTO test = PurchaseServiceImpl.convertOrderToDTO(purchase);
        Assertions.assertInstanceOf(PurchaseDTO.class, test);
    }

    @Test
    void testConvertPurchaseDTOToPurchase() {
        Purchase test = PurchaseServiceImpl.convertDTOToOrder(purchaseDTO);
        Assertions.assertInstanceOf(Purchase.class, test);
    }
}
