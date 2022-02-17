package internship.mbicycle.storify.service;

import internship.mbicycle.storify.dto.StoreDTO;

import java.util.List;

public interface StoreService {
    List<StoreDTO> findStoresByProfileId(Long profileId);

    StoreDTO findStoresByIdAndProfileId(Long id, Long profileId);

    StoreDTO saveStore(StoreDTO storeDTO, Long profileId);

    StoreDTO updateStore(StoreDTO storeDTO, Long id, Long profileId);

    void deleteAllByProfileId(Long profileId);

    void deleteByIdAndProfileId(Long id, Long profileId);

}
