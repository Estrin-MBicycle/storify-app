package internship.mbicycle.storify.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {

    private Long id;

    private LocalDate purchaseDate;

    @NotNull(message = "The field is required")
    @DecimalMin(value = "0", inclusive = false, message = "Value should be > 0. Invalid value")
    private Integer price;

    private String uniqueCode;

    @NotNull(message = "The field is required")
    @DecimalMin(value = "0", inclusive = false, message = "Value should be > 0. Invalid value")
    private Long profileId;

    @NotNull(message = "The field is required")
    private Map<Long, Integer> productDTOMap;

    private boolean delivered;
}
