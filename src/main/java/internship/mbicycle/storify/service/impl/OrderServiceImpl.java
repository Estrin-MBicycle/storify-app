package internship.mbicycle.storify.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

import static internship.mbicycle.storify.util.ExceptionMessage.NOT_FOUND_ORDER;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final GeneratorUniqueCodeImpl generatorUniqueCode;

    public static Order convertDTOToOrder(OrderDTO orderDTO) {
        List<Product> productList = new ArrayList<>();

        if (orderDTO == null) {
            return null;
        }

        List<ProductDTO> productDTOList = orderDTO.getProductDTOList();
        if (productDTOList != null) {
            productList = productDTOList.stream()
                    .map(ProductServiceImpl::convertToProduct)
                    .collect(Collectors.toList());
        }

        return Order.builder()
                .purchaseDate(orderDTO.getPurchaseDate())
                .id(orderDTO.getId())
                .profileId(orderDTO.getProfileId())
                .uniqueCode(orderDTO.getUniqueCode())
                .productList(productList)
                .delivered(orderDTO.isDelivered())
                .build();
    }

    public static OrderDTO convertOrderToDTO(Order order) {
        List<ProductDTO> productDTOList = new ArrayList<>();

        List<Product> productList = order.getProductList();
        if (productList != null) {
            productDTOList = productList.stream()
                    .map(ProductServiceImpl::convertToDTO)
                    .collect(Collectors.toList());
        }

        return OrderDTO.builder()
                .id(order.getId())
                .purchaseDate(order.getPurchaseDate())
                .price(order.getPrice())
                .profileId(order.getProfileId())
                .uniqueCode(order.getUniqueCode())
                .productDTOList(productDTOList)
                .delivered(order.isDelivered())
                .build();
    }

    @Override
    public List<OrderDTO> getAllOrdersByDelivered(boolean isDelivered) {
        return orderRepository.findAllByDelivered(isDelivered).stream()
                .map(OrderServiceImpl::convertOrderToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAllOrdersByProfileIdAndDelivered(Long profileId, boolean isDelivered) {
        return orderRepository.findAllByProfileIdAndDelivered(profileId, isDelivered).stream()
                .map(OrderServiceImpl::convertOrderToDTO)
                .collect(Collectors.toList());
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
        orderDTO.setUniqueCode(generatorUniqueCode.generationUniqueCode());
        orderDTO.setPurchaseDate(LocalDate.now());
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
        return orderRepository.findAllByPurchaseDate(date).stream()
                .map(OrderServiceImpl::convertOrderToDTO)
                .collect(Collectors.toList());
    }
}
