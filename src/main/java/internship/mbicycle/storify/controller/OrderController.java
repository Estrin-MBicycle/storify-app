package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.dto.OrderDTO;
import internship.mbicycle.storify.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{date}")
    public ResponseEntity<List<OrderDTO>> getOrderByDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok(orderService.getAllOrdersByDate(date));
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<List<OrderDTO>> getOrderByProfileId(@PathVariable Long profileId) {
        return ResponseEntity.ok(orderService.getAllOrdersByProfileId(profileId));
    }

    @GetMapping("/{uniqueCode}")
    public ResponseEntity<OrderDTO> getOrderByUniqueCode(@PathVariable String uniqueCode) {
        return ResponseEntity.ok(orderService.getOrderByUniqueCode(uniqueCode));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.saveOrder(orderDTO));
    }

    @PutMapping
    public ResponseEntity<OrderDTO> updateOrder(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.saveOrder(orderDTO));
    }
}
