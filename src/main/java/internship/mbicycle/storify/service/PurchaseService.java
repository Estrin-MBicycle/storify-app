package internship.mbicycle.storify.service;

import java.util.List;

import internship.mbicycle.storify.dto.PurchaseDTO;
import internship.mbicycle.storify.dto.StorifyUserDTO;

public interface PurchaseService {

    PurchaseDTO getPurchaseById(Long id);

    PurchaseDTO getPurchaseByUniqueCode(String uniqueCode);

    List<PurchaseDTO> getAllPurchasesByProfileId(Long profileId);

    PurchaseDTO updatePurchase(PurchaseDTO purchaseDTO, Long id, StorifyUserDTO user);

    List<PurchaseDTO> getAllPurchases();

    PurchaseDTO savePurchase(StorifyUserDTO user, PurchaseDTO purchase);

    List<PurchaseDTO> getAllPurchasesByProfileIdAndDelivered(Long profileId, boolean isDelivered);
}
