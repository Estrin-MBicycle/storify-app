package internship.mbicycle.storify.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import internship.mbicycle.storify.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findAllByPurchaseDate(LocalDate date);

    Optional<Purchase> findPurchaseByUniqueCode(String uniqueCode);

    List<Purchase> findAllByProfileId(Long profileId);

    List<Purchase> findAllByDelivered(boolean isDelivered);

    List<Purchase> findAllByProfileIdAndDelivered(Long profileId, boolean isDelivered);

}
