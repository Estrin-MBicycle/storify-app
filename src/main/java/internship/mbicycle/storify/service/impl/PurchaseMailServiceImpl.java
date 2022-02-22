package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.configuration.properties.MailProperties;
import internship.mbicycle.storify.dto.PurchaseDTO;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.service.PurchaseMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseMailServiceImpl implements PurchaseMailService {

    private final JavaMailSender emailSender;
    private final MailProperties mailProperties;

    @Override
    public void sendMessage(StorifyUser user, PurchaseDTO purchaseDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailProperties.getUsername());
        message.setTo(user.getEmail());
        message.setText(createMessage(user, purchaseDTO));
        emailSender.send(message);
    }

    private String createMessage(StorifyUser user, PurchaseDTO purchaseDTO) {
        return String.format("Dear %s your order for the amount %d (%s),you can get by showing your unique code - %s ",
                user.getUsername(), purchaseDTO.getPrice(), purchaseDTO.getProductDTOMap(), purchaseDTO.getUniqueCode());
    }
}
