package internship.mbicycle.storify.converter;

import internship.mbicycle.storify.dto.StoreDTO;
import internship.mbicycle.storify.model.Store;
import internship.mbicycle.storify.service.impl.ProfileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StoreConverter {

    private final ProfileConverter profileConverter;

    public Store fromStoreDTOToStore(StoreDTO storeDTO) {
        return Store.builder()
                .id(storeDTO.getId())
                .storeName(storeDTO.getStoreName())
                .description(storeDTO.getDescription())
                .address(storeDTO.getAddress())
                .build();
    }

    public StoreDTO fromStoreToStoreDTO(Store store) {
        return StoreDTO.builder()
                .id(store.getId())
                .storeName(store.getStoreName())
                .description(store.getDescription())
                .address(store.getAddress())
                .profileDTO(profileConverter.convertProfileToProfileDTO(store.getProfile()))
                .build();
    }
}
