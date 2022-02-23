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

    @NotBlank(message = "{error.productName.notBlank}")
    @Size(min = 3, max = 50, message = "{error.length.productName}")
    private String productName;

    private String image;

    @NotBlank(message = "{error.description.notBlank}")
    @Size(min = 3, max = 255, message = "{error.length.description}")
    private String description;

    @NotNull(message = "{error.required.field}")
    @DecimalMin(value = "0", inclusive = false, message = "{error.invalid.value}")
    private Integer price;

    @NotNull(message = "{error.required.field}")
    @DecimalMin(value = "0", inclusive = false, message = "{error.invalid.value}")
    private Integer count;

}
