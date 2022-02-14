package internship.mbicycle.storify.repository;

import internship.mbicycle.storify.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findStoresByProfileId(Long profileId);
    Optional<Store> findStoresByIdAndProfileId(Long id, Long profileId);

    @Modifying
    @Query("UPDATE Store s SET s.storeName=:storeName, s.description=:description, s.address=:address WHERE s.id=:id")
    void updateStoreInfo(String storeName, String description, String address, Long id);

}
