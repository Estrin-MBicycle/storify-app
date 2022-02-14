package internship.mbicycle.storify.exception;



import lombok.Getter;
import org.springframework.lang.NonNull;

public class ResourceNotFoundException extends RuntimeException {

    @Getter
    private final ErrorCode errorCode;

    public ResourceNotFoundException(@NonNull ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }



}
