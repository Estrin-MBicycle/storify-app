package internship.mbicycle.storify.util;

public class ExceptionMessage {

    public static final String INVALID_AUTHORIZATION_HEADER = "The authorization header: %s is incorrect";
    public static final String NOT_FOUND_AUTHORIZATION_HEADER = "The authorization header: %s is incorrect";
    public static final String NOT_THE_SAME_TOKENS = "The token from header: %s doesn't match the token from the database";
    public static final String NOT_FOUND_USER = "%s not found in db";
    public static final String NOT_FOUND_STORE = "Store with id %d not found.";
    public static final String NOT_FOUND_PRODUCT = "Product not found.";
    public static final String NOT_FOUND_PROFILE = "Profile with id %d not found.";
    public static final String NOT_FOUND_PURCHASE = "Purchase not found.";
    public static final String VALIDATION_ERROR = "Validation error.";
}
