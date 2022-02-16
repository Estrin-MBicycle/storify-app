package internship.mbicycle.storify.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;
    private LocalDate date;
    private Integer price;
    private String uniqueCode;
    private Long profileId;
    private List<ProductDTO> productDTOList;
    private boolean delivered;
}
