package internship.mbicycle.storify.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProfileDTO {

    private Long id;
    private String name;
    private String surname;
    private String town;
    private String address;
    private String phone;

}
