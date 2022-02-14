package internship.mbicycle.storify.service;

public interface MailService {

    void send(String emailTo, String subject, String message);
}
