package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.dto.OrderDTO;
import internship.mbicycle.storify.service.impl.GeneratorUniqueCodeImpl;
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

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderService;
    private final GeneratorUniqueCodeImpl generatorUniqueCode;

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{date}")
    public ResponseEntity<?> getOrderByDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok(orderService.getAllOrdersByDate(date));
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<?> getOrderByProfileId(@PathVariable Long profileId) {
        return ResponseEntity.ok(orderService.getAllOrdersByProfileId(profileId));
    }

    @GetMapping("/{uniqueCode}")
    public ResponseEntity<?> getOrderByUniqueCode(@PathVariable String uniqueCode) {
        return ResponseEntity.ok(orderService.getOrderByUniqueCode(uniqueCode));
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) {
        orderDTO.setUniqueCode(generatorUniqueCode.generationUniqueCode());
        return ResponseEntity.ok(orderService.saveOrder(orderDTO));
    }

    @PutMapping
    public ResponseEntity<?> updateOrder(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.saveOrder(orderDTO));
    }

    @GetMapping("/{isDelivered}")
    public ResponseEntity<?> getAllDeliveredOrders(@PathVariable boolean isDelivered) {
        return ResponseEntity.ok(orderService.getAllOrdersByDelivered(isDelivered));
    }

    @GetMapping("/{profileId}/{isDelivered}")
    public ResponseEntity<?> getAllDeliveredOrdersByProfileId(@PathVariable Long profileId,
                                                                 @PathVariable boolean isDelivered) {
        return ResponseEntity.ok(orderService.getAllOrdersByProfileIdAndDelivered(profileId, isDelivered));
    }
}
