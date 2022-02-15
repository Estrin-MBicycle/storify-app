package internship.mbicycle.storify.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ErrorData> handleUserNotFoundException(ResourceNotFoundException ex,
                                                                       WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ErrorData error = new ErrorData(ErrorCode.NOT_FOUND_STORE, details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
