package internship.mbicycle.storify.exception;

import java.util.List;

/**
 * ErrorInformation class. Its only purpose is to return information about the detected error.
 */
public class ErrorInformation {
    public ErrorInformation(String message, List<String> details) {
        super();
        this.message = message;
        this.details = details;
    }

    private String message;
    private List<String> details;

    public ErrorInformation(ErrorCode message, List<String> details) {
        this(message.getMessage(), details);

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}