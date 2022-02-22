package internship.mbicycle.storify.converter;

import internship.mbicycle.storify.dto.PurchaseDTO;
import internship.mbicycle.storify.model.Purchase;
import org.springframework.stereotype.Component;

@Component
public class PurchaseConverter {

    public Purchase convertPurchaseDTOToPurchase(PurchaseDTO purchaseDTO) {
        if (purchaseDTO == null) {
            return null;
        }

        return Purchase.builder()
                .purchaseDate(purchaseDTO.getPurchaseDate())
                .id(purchaseDTO.getId())
                .profileId(purchaseDTO.getProfileId())
                .uniqueCode(purchaseDTO.getUniqueCode())
                .products(purchaseDTO.getProductDTOMap())
                .delivered(purchaseDTO.isDelivered())
                .build();
    }

    public PurchaseDTO convertPurchaseToPurchaseDTO(Purchase purchase) {

        return PurchaseDTO.builder()
                .id(purchase.getId())
                .purchaseDate(purchase.getPurchaseDate())
                .price(purchase.getPrice())
                .profileId(purchase.getProfileId())
                .uniqueCode(purchase.getUniqueCode())
                .productDTOMap(purchase.getProducts())
                .delivered(purchase.isDelivered())
                .build();
    }
}
