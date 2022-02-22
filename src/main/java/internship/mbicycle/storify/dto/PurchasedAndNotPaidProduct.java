package internship.mbicycle.storify.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchasedAndNotPaidProduct {
    String productName;
    Long profit;
}
