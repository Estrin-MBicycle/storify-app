package internship.mbicycle.storify.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StorifyUserDTO {

    @NotBlank(message = "Please fill the email")
    @Email(message = "Not correct email")
    private String email;

    @NotBlank(message = "Please fill the password")
    @Length(min = 6, message = "Please enter the more long password")
    private String password;

    @NotBlank(message = "Please fill the name")
    private String name;
}
