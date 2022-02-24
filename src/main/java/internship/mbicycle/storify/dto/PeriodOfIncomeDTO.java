package internship.mbicycle.storify.dto;

import internship.mbicycle.storify.util.PeriodOfIncome;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeriodOfIncomeDTO {

    private Map<PeriodOfIncome, Integer> income;

}
