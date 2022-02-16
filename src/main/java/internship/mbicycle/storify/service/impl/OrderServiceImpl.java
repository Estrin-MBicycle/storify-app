package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.dto.OrderDTO;
import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.model.Order;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.repository.OrderRepository;
import internship.mbicycle.storify.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static internship.mbicycle.storify.exception.ErrorCode.NOT_FOUND_ORDER;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public static Order convertDTOToOrder(OrderDTO orderDTO) {
        if (orderDTO == null) {
            return null;
        }
        List<Product> productList = orderDTO.getProductDTOList().stream()
                .map(ProductServiceImpl::convertToProduct)
                .collect(Collectors.toList());

        return Order.builder()
                .date(orderDTO.getDate())
                .id(orderDTO.getId())
                .profileId(orderDTO.getProfileId())
                .uniqueCode(orderDTO.getUniqueCode())
                .productList(productList)
                .build();
    }

    public static OrderDTO convertOrderToDTO(Order order) {

        List<ProductDTO> productDTOList = order.getProductList().stream()
                .map(ProductServiceImpl::convertToDTO)
                .collect(Collectors.toList());

        return OrderDTO.builder()
                .id(order.getId())
                .date(order.getDate())
                .price(order.getPrice())
                .profileId(order.getProfileId())
                .uniqueCode(order.getUniqueCode())
                .productDTOList(productDTOList)
                .build();
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(NOT_FOUND_ORDER));
        return convertOrderToDTO(order);
    }

    @Override
    public OrderDTO getOrderByUniqueCode(String uniqueCode) {
        Order order = orderRepository.findOrderByUniqueCode(uniqueCode).orElseThrow(() ->
                new ResourceNotFoundException(NOT_FOUND_ORDER));
        return convertOrderToDTO(order);
    }

    @Override
    public List<OrderDTO> getAllOrdersByProfileId(Long profileId) {
        return orderRepository.findAllByProfileId(profileId).stream()
                .map(OrderServiceImpl::convertOrderToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO saveOrder(OrderDTO orderDTO) {
        orderRepository.save(convertDTOToOrder(orderDTO));
        return orderDTO;
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderServiceImpl::convertOrderToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAllOrdersByDate(LocalDate date) {
        return orderRepository.findAllByDate(date).stream()
                .map(OrderServiceImpl::convertOrderToDTO)
                .collect(Collectors.toList());
    }
}
