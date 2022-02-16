package internship.mbicycle.storify.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND_STORE("Store not found."),
    NOT_FOUND_PRODUCT("Product not found."),
    NOT_FOUND_PROFILE("Profile not found."),
    STORE_IS_NULL("Store is null");

    @Getter
    private final String message;
}
