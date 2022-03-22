package internship.mbicycle.storify.controller;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import internship.mbicycle.storify.dto.CartDTO;
import internship.mbicycle.storify.dto.PurchaseDTO;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.service.PurchaseService;
import internship.mbicycle.storify.service.StorifyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final StorifyUserService storifyUserService;

    @GetMapping
    public List<PurchaseDTO> getAllPurchases() {
        return purchaseService.getAllPurchases();
    }

    @GetMapping("/{uniqueCode}")
    public PurchaseDTO getPurchaseByUniqueCode(@PathVariable String uniqueCode) {
        return purchaseService.getPurchaseByUniqueCode(uniqueCode);
    }

    @PostMapping
    public PurchaseDTO createPurchase(@Valid @RequestBody CartDTO cartDTO,
                                      @ApiIgnore Principal principal) {
        StorifyUser user = storifyUserService.getUserByEmail(principal.getName());
        return purchaseService.savePurchase(user, cartDTO);
    }

    @PutMapping("/{id}")
    public PurchaseDTO updatePurchase(@Valid @RequestBody PurchaseDTO purchaseDTO,
                                      @PathVariable Long id,
                                      @ApiIgnore Principal principal) {
        StorifyUser user = storifyUserService.getUserByEmail(principal.getName());
        return purchaseService.updatePurchase(purchaseDTO, id, user);
    }

    @GetMapping("/{profileId}/{isDelivered}")
    public List<PurchaseDTO> getAllDeliveredPurchasesByProfileId(@PathVariable Long profileId,
                                                                 @PathVariable boolean isDelivered) {
        return purchaseService.getAllPurchasesByProfileIdAndDelivered(profileId, isDelivered);
    }
}
