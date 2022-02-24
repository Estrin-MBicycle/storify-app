package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.dto.PurchaseDTO;
import internship.mbicycle.storify.model.StorifyUser;
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

import javax.validation.Valid;

@RestController
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping
    public ResponseEntity<?> getAllPurchases() {
        return ResponseEntity.ok(purchaseService.getAllPurchases());
    }

    @GetMapping("/{uniqueCode}")
    public ResponseEntity<?> getPurchaseByUniqueCode(@PathVariable String uniqueCode) {
        return ResponseEntity.ok(purchaseService.getPurchaseByUniqueCode(uniqueCode));
    }

    @PostMapping
    public ResponseEntity<?> createPurchase(@Valid @RequestBody PurchaseDTO purchaseDTO,
                                            @RequestBody StorifyUser user) {
        return ResponseEntity.ok(purchaseService.savePurchase(user, purchaseDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePurchase(@Valid @RequestBody PurchaseDTO purchaseDTO,
                                            @PathVariable Long id,
                                            @RequestBody StorifyUser user) {
        return ResponseEntity.ok(purchaseService.updatePurchase(purchaseDTO, id, user));
    }

    @GetMapping("/{profileId}/{isDelivered}")
    public ResponseEntity<?> getAllDeliveredPurchasesByProfileId(@PathVariable Long profileId,
                                                                 @PathVariable boolean isDelivered) {
        return ResponseEntity.ok(purchaseService.getAllPurchasesByProfileIdAndDelivered(profileId, isDelivered));
    }
}
