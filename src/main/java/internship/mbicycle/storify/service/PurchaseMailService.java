package internship.mbicycle.storify.service;

import internship.mbicycle.storify.dto.PurchaseDTO;
import internship.mbicycle.storify.model.StorifyUser;

public interface PurchaseMailService {

    void sendMessage(StorifyUser user, PurchaseDTO purchaseDTO);
}
