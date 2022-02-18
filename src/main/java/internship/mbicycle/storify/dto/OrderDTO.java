package internship.mbicycle.storify.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;
    private LocalDate purchaseDate;
    private Integer price;
    private String uniqueCode;
    private Long profileId;
    private List<ProductDTO> productDTOList;
    private boolean delivered;
}
