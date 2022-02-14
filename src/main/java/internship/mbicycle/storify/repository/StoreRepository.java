package internship.mbicycle.storify.repository;

import internship.mbicycle.storify.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findStoresByProfileId(Long profileId);
    Optional<Store> findStoresByIdAndProfileId(Long id, Long profileId);
}
