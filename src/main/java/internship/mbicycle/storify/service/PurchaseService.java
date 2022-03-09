package internship.mbicycle.storify.service;

import java.util.List;

import internship.mbicycle.storify.dto.PurchaseDTO;
import internship.mbicycle.storify.model.StorifyUser;

public interface PurchaseService {

    PurchaseDTO getPurchaseById(Long id);

    PurchaseDTO getPurchaseByUniqueCode(String uniqueCode);

    List<PurchaseDTO> getAllPurchasesByProfileId(Long profileId);

    PurchaseDTO updatePurchase(PurchaseDTO purchaseDTO, Long id, StorifyUser user);

    List<PurchaseDTO> getAllPurchases();

    PurchaseDTO savePurchase(StorifyUser user, PurchaseDTO purchase);

    List<PurchaseDTO> getAllPurchasesByProfileIdAndDelivered(Long profileId, boolean isDelivered);
}
