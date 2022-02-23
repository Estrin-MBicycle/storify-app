package internship.mbicycle.storify.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Name must not be empty")
    @Size(min = 3, max = 50, message = "Product mame must have [3..50] length")
    private String productName;

    private String image;

    @NotBlank(message = "Description must not be empty")
    @Size(min = 3, max = 255, message = "Description must have [3..255] length")
    private String description;

    @NotNull(message = "The field is required")
    @DecimalMin(value = "0", inclusive = false, message = "Invalid value")
    private Integer price;

    @NotNull(message = "The field is required")
    @DecimalMin(value = "0", inclusive = false, message = "Invalid value")
    private Integer count;

}
