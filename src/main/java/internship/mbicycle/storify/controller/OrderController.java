package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.dto.OrderDTO;
import internship.mbicycle.storify.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{uniqueCode}")
    public ResponseEntity<?> getOrderByUniqueCode(@PathVariable String uniqueCode) {
        return ResponseEntity.ok(orderService.getOrderByUniqueCode(uniqueCode));
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.saveOrder(orderDTO));
    }

    @PutMapping
    public ResponseEntity<?> updateOrder(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.saveOrder(orderDTO));
    }

    @GetMapping("/{profileId}/{isDelivered}")
    public ResponseEntity<?> getAllDeliveredOrdersByProfileId(@PathVariable Long profileId,
                                                              @PathVariable boolean isDelivered) {
        return ResponseEntity.ok(orderService.getAllOrdersByProfileIdAndDelivered(profileId, isDelivered));
    }
}
