package internship.mbicycle.storify.service;

import internship.mbicycle.storify.dto.PurchaseDTO;
import internship.mbicycle.storify.model.StorifyUser;

public interface MailService {

    void send(String emailTo, String subject, String message);
    void sendPurchaseMessage(StorifyUser user, PurchaseDTO purchaseDTO);
}
