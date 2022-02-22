package internship.mbicycle.storify.dto;

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
    private Integer price;
    private String uniqueCode;
    private Long profileId;
    private Map<Long, Integer> productDTOMap;
    private boolean delivered;
}
