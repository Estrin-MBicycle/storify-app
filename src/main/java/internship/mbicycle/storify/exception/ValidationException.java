package internship.mbicycle.storify.exception;

import lombok.Getter;

public class ValidationException extends RuntimeException {

    @Getter
    private String message;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, String errorMessage) {
        super(message);
        this.message = errorMessage;
    }

}
