package internship.mbicycle.storify.service;

import java.util.List;

import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.dto.PurchaseDTO;
import internship.mbicycle.storify.dto.StorifyUserDTO;

public interface MailService {

    void send(String emailTo, String subject, String message);

    void sendPurchaseMessage(StorifyUserDTO user, PurchaseDTO purchaseDTO);

    void sendFavoriteMessage(List<String> emails, ProductDTO productDTO);
}
