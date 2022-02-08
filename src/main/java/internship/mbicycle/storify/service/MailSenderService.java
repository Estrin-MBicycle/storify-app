package internship.mbicycle.storify.service;

public interface MailSenderService {

    void send(String emailTo, String subject, String message);
}
