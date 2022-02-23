package internship.mbicycle.storify.exception;

import lombok.Getter;
import org.springframework.lang.NonNull;

public class ValidationException extends RuntimeException {

    @Getter
    private final ErrorCode errorCode;

    public ValidationException(@NonNull ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ValidationException(@NonNull ErrorCode errorCode, @NonNull String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ValidationException(@NonNull ErrorCode errorCode, @NonNull String message, Object... args) {
        super(String.format(message, args));
        this.errorCode = errorCode;
    }
}
