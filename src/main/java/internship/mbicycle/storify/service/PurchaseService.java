package internship.mbicycle.storify.service;

import java.security.Principal;
import java.util.List;

import internship.mbicycle.storify.dto.CartDTO;
import internship.mbicycle.storify.dto.PurchaseDTO;
import internship.mbicycle.storify.model.StorifyUser;

public interface PurchaseService {

    PurchaseDTO getPurchaseById(Long id);

    PurchaseDTO getPurchaseByUniqueCode(String uniqueCode);

    List<PurchaseDTO> getAllPurchasesByProfileId(Long profileId);

    List<PurchaseDTO> getAllPurchasesByProfile(Principal principal);

    PurchaseDTO updatePurchase(PurchaseDTO purchaseDTO, Long id, StorifyUser user);

    List<PurchaseDTO> getAllPurchases();

    PurchaseDTO savePurchase(StorifyUser user, CartDTO cartDTO);

    List<PurchaseDTO> getAllPurchasesByProfileIdAndDelivered(Long profileId, boolean isDelivered);
}
