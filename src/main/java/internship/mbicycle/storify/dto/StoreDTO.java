package internship.mbicycle.storify.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreDTO {
    private Long id;

    @NotBlank(message = "storeName is required field")
    @Size(max = 50, message = "storeName must be max 50 symbols")
    private String storeName;

    @NotBlank(message = "description is required field")
    @Size(max = 255, message = "description must be max 255 symbols")
    private String description;

    @NotBlank(message = "address is required field")
    @Size(max = 255, message = "address must be max 255 symbols")
    private String address;
}
