package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.dto.StoreDTO;
import internship.mbicycle.storify.exception.ErrorCode;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.model.Store;
import internship.mbicycle.storify.repository.StoreRepository;
import internship.mbicycle.storify.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    //нужен ProfileService
    //   private final ProfileService profileService;

    @Override
    public List<StoreDTO> findStoresByProfileId(Long profileId) {
        return storeRepository.findStoresByProfileId(profileId)
                .stream()
                .map(store -> fromStoreToStoreDTO(store))
                .collect(Collectors.toList());
    }

    @Override
    public StoreDTO findStoresByIdAndProfileId(Long id, Long profileId) {
        Store store = storeRepository.findStoresByIdAndProfileId(id, profileId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.NOT_FOUND_STORE));
        return fromStoreToStoreDTO(store);
    }

    @Override
    public StoreDTO saveStore(StoreDTO storeDTO) {
        Store store = fromStoreDTOToStore(storeDTO);
        storeRepository.save(store);
        return storeDTO;
    }

    @Override
    public void deleteByIdAndProfileId(Long id, Long profileId) {
        storeRepository.deleteByIdAndProfileId(id, profileId);
    }

    @Override
    public void deleteAllByProfileId(Long profileId) {
        storeRepository.deleteAllByProfileId(profileId);
    }


    public static Store fromStoreDTOToStore(StoreDTO storeDTO) {
        if (storeDTO == null) {
            return null;
        }
        Store store = new Store();
        store.setId(storeDTO.getId());
        store.setStoreName(storeDTO.getStoreName());
        store.setDescription(storeDTO.getDescription());
        store.setAddress(storeDTO.getAddress());
        store.setProfit(storeDTO.getProfit());
        return store;
    }

    public static StoreDTO fromStoreToStoreDTO(Store store) {
        if (store == null) {
            return null;
        }
        //    target.setFileType(fileTypeConverter.toDto(source.getFileType()));
        return StoreDTO.builder()
                .id(store.getId())
                .storeName(store.getStoreName())
                .description(store.getDescription())
                .address(store.getAddress())
                .profit(store.getProfit())
                //   .profileDTO()
                .build();
    }
}