package internship.mbicycle.storify.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND_STORE("Store not found.");

    @Getter
    private final String message;
}
