package internship.mbicycle.storify.service;

import java.util.List;

import internship.mbicycle.storify.dto.PurchaseDTO;

public interface PurchaseService {

    PurchaseDTO getPurchaseById(Long id);

    PurchaseDTO getPurchaseByUniqueCode(String uniqueCode);

    List<PurchaseDTO> getAllPurchasesByProfileId(Long profileId);

    List<PurchaseDTO> getAllPurchases();

    PurchaseDTO savePurchase(PurchaseDTO order);

    List<PurchaseDTO> getAllPurchasesByProfileIdAndDelivered(Long profileId, boolean isDelivered);
}
