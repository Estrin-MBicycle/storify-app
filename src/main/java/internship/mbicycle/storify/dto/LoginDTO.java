package internship.mbicycle.storify.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static internship.mbicycle.storify.util.ExceptionMessage.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDTO {

    @NotBlank(message = EMPTY_EMAIL_EXCEPTION)
    @Email(message = INVALID_EMAIL_EXCEPTION)
    private String email;

    @Length(min = 6, message = INVALID_PASSWORD_EXCEPTION)
    private String password;
}
