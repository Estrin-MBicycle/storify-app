package internship.mbicycle.storify.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;

import internship.mbicycle.storify.dto.PurchaseDTO;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.model.Purchase;
import internship.mbicycle.storify.repository.PurchaseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PurchaseServiceImplTest {

    private PurchaseServiceImpl orderService;
    private Purchase purchase;
    private PurchaseDTO purchaseDTO;

    @BeforeEach
    void setUp() {
        orderService = new PurchaseServiceImpl(Mockito.mock(PurchaseRepository.class),
            Mockito.mock(GeneratorUniqueCodeImpl.class));

        purchase = Purchase.builder()
            .id(87L)
            .delivered(false)
            .purchaseDate(LocalDate.now())
            .build();

        purchaseDTO = PurchaseDTO.builder()
            .delivered(false)
            .id(55L)
            .purchaseDate(LocalDate.now())
            .build();

    }

    @Test
    void testFindByIncorrectId() {
        assertThrows(ResourceNotFoundException.class, () -> orderService.getPurchaseById(-4L));
    }

    @Test
    void testMessageFindByIncorrectId() {
        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () ->
            orderService.getPurchaseByUniqueCode("0"));
        Assertions.assertEquals("Order not found.", thrown.getMessage());
    }

    @Test
    void testFindByProfileId() {
        List<PurchaseDTO> list = orderService.getAllPurchasesByProfileId(0L);
        Assertions.assertTrue(list.isEmpty());
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
