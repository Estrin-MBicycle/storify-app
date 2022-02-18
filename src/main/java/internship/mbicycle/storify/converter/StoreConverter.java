package internship.mbicycle.storify.converter;

import internship.mbicycle.storify.dto.StoreDTO;
import internship.mbicycle.storify.model.Store;
import internship.mbicycle.storify.service.impl.ProfileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StoreConverter {

 //   private final ProfileConverter profileConverter; //

    public Store fromStoreDTOToStore(StoreDTO storeDTO) {
        if (storeDTO == null) {
            return null;
        }
        Store store = new Store();
        store.setId(storeDTO.getId());
        store.setStoreName(storeDTO.getStoreName());
        store.setDescription(storeDTO.getDescription());
        store.setAddress(storeDTO.getAddress());
        return store;
    }

    public StoreDTO fromStoreToStoreDTO(Store store) {
        if (store == null) {
            return null;
        }
        return StoreDTO.builder()
                .id(store.getId())
                .storeName(store.getStoreName())
                .description(store.getDescription())
                .address(store.getAddress())
     //           .profileDTO(ProfileServiceImpl.convertProfileToProfileDTO(store.getProfile()))
                .build();
    }
}
