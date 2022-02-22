package internship.mbicycle.storify.exception;

public class StorifyUserNotFoundException extends RuntimeException{
    public StorifyUserNotFoundException(String message) {
        super(message);
    }
}
