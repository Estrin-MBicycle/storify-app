package internship.mbicycle.storify.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ErrorCode {

    VALIDATION_ERROR("Validation error.");

    @Getter
    private final String message;

}
