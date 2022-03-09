package internship.mbicycle.storify.service;

import internship.mbicycle.storify.dto.PurchaseDTO;
import internship.mbicycle.storify.model.StorifyUser;

import java.security.Principal;
import java.util.List;

public interface PurchaseService {

    PurchaseDTO getPurchaseById(Long id);

    PurchaseDTO getPurchaseByUniqueCode(String uniqueCode);

    List<PurchaseDTO> getAllPurchasesByProfileId(Long profileId);

    List<PurchaseDTO> getAllPurchasesByProfile(Principal principal);

    PurchaseDTO updatePurchase(PurchaseDTO purchaseDTO, Long id, StorifyUser user);

    List<PurchaseDTO> getAllPurchases();

    PurchaseDTO savePurchase(StorifyUser user, PurchaseDTO purchase);

    List<PurchaseDTO> getAllPurchasesByProfileIdAndDelivered(Long profileId, boolean isDelivered);
}
