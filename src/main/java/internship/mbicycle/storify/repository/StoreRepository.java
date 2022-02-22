package internship.mbicycle.storify.repository;

import internship.mbicycle.storify.dto.PurchasedAndNotPaidProduct;
import internship.mbicycle.storify.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findStoresByProfileId(Long profileId);

    Optional<Store> findStoresByIdAndProfileId(Long id, Long profileId);

    void deleteAllByProfileId(Long profileId);

    void deleteByIdAndProfileId(Long id, Long profileId);

    @Query(name = "find_most_purchased_products", nativeQuery = true)
    List<PurchasedAndNotPaidProduct> findMostPurchasedProductsInStore(@Param("id") Long id, @Param("limit") Long limit);

    @Query(name = "find_least_purchased_products", nativeQuery = true)
    List<PurchasedAndNotPaidProduct> findLestPurchasedProductsInStore(@Param("id") Long id, @Param("limit") Long limit);

}
