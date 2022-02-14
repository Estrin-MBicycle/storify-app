package internship.mbicycle.storify.service;

import internship.mbicycle.storify.model.Store;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreService {
    List<Store> findStoresByProfileId(Long profileId);

    Optional<Store> findStoresByIdAndProfileId(Long id, Long profileId);

    void updateStoreInfo(String storeName, String description, String address, Long id);
}
