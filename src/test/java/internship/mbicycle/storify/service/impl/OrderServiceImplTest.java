package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.dto.OrderDTO;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderServiceImplTest {

    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl(Mockito.mock(OrderRepository.class),
                Mockito.mock(GeneratorUniqueCodeImpl.class));
    }

    @Test
    void testFindByIncorrectId() {
        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(-4L));
    }

    @Test
    void testMessageFindByIncorrectId() {
        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () ->
                orderService.getOrderByUniqueCode("0"));
        Assertions.assertEquals("Order not found.", thrown.getMessage());
    }

    @Test
    void testFindByProfileId() {
        List<OrderDTO> list = orderService.getAllOrdersByProfileId(0L);
        Assertions.assertTrue(list.isEmpty());
    }
}
