package internship.mbicycle.storify.exception;

import lombok.Getter;

public class ResourceNotFoundException extends RuntimeException {

    @Getter
    private final ErrorCode errorCode;

    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
