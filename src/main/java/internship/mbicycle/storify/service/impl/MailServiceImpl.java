package internship.mbicycle.storify.service.impl;

import java.util.List;

import internship.mbicycle.storify.configuration.properties.MailProperties;
import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.dto.PurchaseDTO;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;

    @Override
    public void send(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailProperties.getUsername());
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    @Override
    public void sendPurchaseMessage(StorifyUser user, PurchaseDTO purchaseDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailProperties.getUsername());
        message.setTo(user.getEmail());
        message.setText(createPurchaseMessage(user, purchaseDTO));
        mailSender.send(message);
    }

    @Override
    public void sendFavoriteMessage(List<String> emails, ProductDTO productDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailProperties.getUsername());
        emails.forEach(email -> {
            message.setTo(email);
            message.setText(createFavoriteMessage(productDTO));
            mailSender.send(message);
        });

    }

    private String createPurchaseMessage(StorifyUser user, PurchaseDTO purchaseDTO) {
        return String.format("Dear %s your order for the amount %d (%s),you can get by showing your unique code - %s ",
            user.getName(), purchaseDTO.getPrice(), purchaseDTO.getProductDTOMap(), purchaseDTO.getUniqueCode());
    }

    private String createFavoriteMessage(ProductDTO productDTO) {
        return String.format("Your favorite product  %s is on sale again.", productDTO.getProductName());
    }
}
