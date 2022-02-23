package internship.mbicycle.storify.exception;

public class InvalidAuthorizationHeaderException extends RuntimeException{
    public InvalidAuthorizationHeaderException(String message) {
        super(message);
    }
}
