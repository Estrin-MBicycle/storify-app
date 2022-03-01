package internship.mbicycle.storify.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static internship.mbicycle.storify.util.ExceptionMessage.EMPTY_EMAIL_EXCEPTION;
import static internship.mbicycle.storify.util.ExceptionMessage.INVALID_EMAIL_EXCEPTION;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewEmailDTO {

    @NotBlank(message = EMPTY_EMAIL_EXCEPTION)
    @Email(message = INVALID_EMAIL_EXCEPTION)
    private String email;
}