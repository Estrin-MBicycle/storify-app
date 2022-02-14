package internship.mbicycle.storify.exception;

/**
 * ErrorInformation class. Its only purpose is to return information about the detected error.
 */
public class ErrorInformation {
    private String message;
    private String exception;

    public ErrorInformation(String message, Exception ex) {
        this.message = message;
        if (ex != null) {
            this.exception = ex.getLocalizedMessage();
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getException() {
        return exception;
    }
}
