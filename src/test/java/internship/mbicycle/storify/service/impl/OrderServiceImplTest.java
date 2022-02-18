package internship.mbicycle.storify.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;

import internship.mbicycle.storify.dto.OrderDTO;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.model.Order;
import internship.mbicycle.storify.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class OrderServiceImplTest {

    private OrderServiceImpl orderService;
    private Order order;
    private OrderDTO orderDTO;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl(Mockito.mock(OrderRepository.class),
                Mockito.mock(GeneratorUniqueCodeImpl.class));

        order = Order.builder()
                .id(87L)
                .delivered(false)
                .purchaseDate(LocalDate.now())
                .build();

        orderDTO = OrderDTO.builder()
                .delivered(false)
                .id(55L)
                .purchaseDate(LocalDate.now())
                .build();

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

    @Test
    void testConvertOrderToOrderDTO() {
        OrderDTO test = OrderServiceImpl.convertOrderToDTO(order);
        Assertions.assertInstanceOf(OrderDTO.class, test);
    }

    @Test
    void testConvertOrderDTOToOrder() {
        Order test = OrderServiceImpl.convertDTOToOrder(orderDTO);
        Assertions.assertInstanceOf(Order.class, test);
    }
}
