package internship.mbicycle.storify.service;

import internship.mbicycle.storify.model.Store;

import java.util.List;
import java.util.Optional;

public interface StoreService {
    List<Store> findStoresByProfileId(Long profileId);

    Optional<Store> findStoresByProfileIdAndId(Long profileId, Long id);
}
