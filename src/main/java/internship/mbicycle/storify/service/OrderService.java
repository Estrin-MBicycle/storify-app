package internship.mbicycle.storify.service;

import internship.mbicycle.storify.dto.OrderDTO;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    OrderDTO getOrderById(Long id);

    OrderDTO getOrderByUniqueCode(String uniqueCode);

    List<OrderDTO> getAllOrdersByProfileId(Long profileId);

    List<OrderDTO> getAllOrdersByDate(LocalDate date);

    List<OrderDTO> getAllOrders();

    OrderDTO saveOrder(OrderDTO order);

    List<OrderDTO> getAllOrdersByDelivered(boolean isDelivered);

    List<OrderDTO> getAllOrdersByProfileIdAndDelivered(Long profileId, boolean isDelivered);
}
