package internship.mbicycle.storify.repository;

import internship.mbicycle.storify.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findStoresByProfileId(Long profileId);

    Optional<Store> findStoresByIdAndProfileId(Long id, Long profileId);

    void deleteAllByProfileId(Long profileId);

    void deleteByIdAndProfileId(Long id, Long profileId);
/*  This query doesn't work now
    @Query(name = "select p.product_name name, count(p.product_name) 'count' " +
            "from purchase pu " +
            "inner join product_purchase pp " +
            "on pu.id = pp.purchase_id " +
            "inner join product p " +
            "on pp.product_id = p.id " +
            "where p.store_id = :id " +
            "group by name " +
            "order by count(name) desc " +
            "limit :limit", nativeQuery = true)
    List<String> findMostPurchasedProductsInStore(Long id, Long limit);
*/
}
