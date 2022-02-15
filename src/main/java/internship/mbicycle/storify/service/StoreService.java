package internship.mbicycle.storify.service;

import internship.mbicycle.storify.dto.StoreDTO;
import internship.mbicycle.storify.model.Store;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreService {
    List<StoreDTO> findStoresByProfileId(Long profileId);

    StoreDTO findStoresByIdAndProfileId(Long id, Long profileId);

    StoreDTO saveStore(StoreDTO storeDTO);

    void deleteAllByProfileId(Long profileId);

    void deleteByIdAndProfileId(Long id, Long profileId);

}
