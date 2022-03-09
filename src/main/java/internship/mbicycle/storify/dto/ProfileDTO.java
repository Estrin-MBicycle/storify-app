package internship.mbicycle.storify.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProfileDTO {

    @NotBlank(message = "name is required field")
    @Size(max = 50, message = "name must be max 50 symbols")
    private String name;

    @NotBlank(message = "surname is required field")
    @Size(max = 50, message = "surname must be max 50 symbols")
    private String surname;

    @NotBlank(message = "town is required field")
    @Size(max = 50, message = "town must be max 50 symbols")
    private String town;

    @NotBlank(message = "address is required field")
    @Size(max = 50, message = "address must be max 50 symbols")
    private String address;

    @Pattern(regexp = "^\\+375\\((?:25|29|33|44)\\)\\d{3}-\\d{2}-\\d{2}$",
            message = "phone number is not valid (+375(25|29|33|44)222-33-44)")
    private String phone;

}
