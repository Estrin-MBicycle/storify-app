package internship.mbicycle.storify.util;

public class EmailMessage {

    public static final String REGISTRATION_CONFIRMATION_CODE = "Activation code";
    public static final String CONFIRMATION_CODE = "Confirmation code";
    public static final String ACTIVATION_GREETING = "Hello, %s! \n" +
            "Thank you for signing up for Storify! Your activation link is: http://localhost:8080/activate/%s" +
            "\nPlease click that link to confirm your email address and complete your registration";
    public static final String CONFIRMATION_EMAIL = "Hello, %s! \n " +
            "To confirm the changing your email. Please, visit next link: http://localhost:8080/email/%s";
    private EmailMessage() {
    }
}
