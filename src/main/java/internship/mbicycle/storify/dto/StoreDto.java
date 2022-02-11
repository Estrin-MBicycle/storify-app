package internship.mbicycle.storify.dto;

import lombok.Data;

@Data
public class StoreDto {
    private Long id;
    private String storeName;
    private String description;
    private String address;
    private Long profit;

// нужен ProfileDto
//    private ProfileDto profileDto;

}
