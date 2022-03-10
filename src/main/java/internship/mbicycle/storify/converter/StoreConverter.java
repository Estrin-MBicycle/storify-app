package internship.mbicycle.storify.converter;

import internship.mbicycle.storify.dto.StoreDTO;
import internship.mbicycle.storify.model.Store;
import org.springframework.stereotype.Component;

@Component
public class StoreConverter {

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
                .build();
    }
}
