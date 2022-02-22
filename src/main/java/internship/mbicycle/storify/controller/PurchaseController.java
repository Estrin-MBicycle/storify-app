package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.dto.PurchaseDTO;
import internship.mbicycle.storify.service.PurchaseService;
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
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(purchaseService.getAllPurchases());
    }

    @GetMapping("/{uniqueCode}")
    public ResponseEntity<?> getOrderByUniqueCode(@PathVariable String uniqueCode) {
        return ResponseEntity.ok(purchaseService.getPurchaseByUniqueCode(uniqueCode));
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody PurchaseDTO purchaseDTO) {
        return ResponseEntity.ok(purchaseService.savePurchase(purchaseDTO));
    }

    @PutMapping
    public ResponseEntity<?> updateOrder(@RequestBody PurchaseDTO purchaseDTO) {
        return ResponseEntity.ok(purchaseService.savePurchase(purchaseDTO));
    }

    @GetMapping("/{profileId}/{isDelivered}")
    public ResponseEntity<?> getAllDeliveredOrdersByProfileId(@PathVariable Long profileId,
                                                              @PathVariable boolean isDelivered) {
        return ResponseEntity.ok(purchaseService.getAllPurchasesByProfileIdAndDelivered(profileId, isDelivered));
    }
}
