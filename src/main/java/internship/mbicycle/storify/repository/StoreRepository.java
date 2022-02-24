package internship.mbicycle.storify.repository;


import internship.mbicycle.storify.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findStoresByProfileId(Long profileId);

    Optional<Store> findStoreByIdAndProfileId(Long id, Long profileId);

    List<Store> findStoresByProfileIdNot(Long profileId);

    Optional<Store> findStoreById(Long id);

    void deleteAllByProfileId(Long profileId);

    void deleteByIdAndProfileId(Long id, Long profileId);

    @Query(value = "select p.product_name productName, sum(p.price*pp.count) profit "+
            "from purchase pu "+
            "inner join product_purchase pp on pu.id = pp.purchase "+
            "inner join product p on pp.product = p.id "+
            "where p.store_id = :id "+
            "group by productName "+
            "order by sum(p.price*pp.count) desc "+
            "limit :limit", nativeQuery = true)
    List<PurchasedAndNotPaidProduct> findMostPurchasedProductsInStore(@Param("id") Long id, @Param("limit") Long limit);

    @Query(value = "select p.product_name productName, sum(p.price*pp.count) profit "+
            "from purchase pu "+
            "inner join product_purchase pp on pu.id = pp.purchase "+
            "inner join product p on pp.product = p.id "+
            "where p.store_id = :id "+
            "group by productName "+
            "order by sum(p.price*pp.count) "+
            "limit :limit", nativeQuery = true)
    List<PurchasedAndNotPaidProduct> findLestPurchasedProductsInStore(@Param("id") Long id, @Param("limit") Long limit);

    public static interface PurchasedAndNotPaidProduct {
        String getProductName();
        String getProfit();
    }

    @Query(value = "SELECT SUM(pr.price * pp.count) " +
            "FROM store s INNER JOIN product pr on s.id = pr.store_id " +
            "INNER JOIN product_purchase pp on pr.id = pp.product " +
            "INNER JOIN purchase pu on pp.purchase = pu.id " +
            "WHERE s.profile_id = :profileId", nativeQuery = true)
    Integer getIncomeForAllTime(@Param("profileId") long profileId);

    @Query(value = "SELECT SUM(pr.price * pp.count) " +
            "FROM store s INNER JOIN product pr on s.id = pr.store_id " +
            "INNER JOIN product_purchase pp on pr.id = pp.product " +
            "INNER JOIN purchase pu on pp.purchase = pu.id " +
            "WHERE s.profile_id = :profileId AND " +
            "pu.purchase_date BETWEEN CURDATE() - INTERVAL :month MONTH AND CURDATE()", nativeQuery = true)
    Integer getIncomeForMonths(@Param("profileId") long profileId, @Param("month") int month);

    @Query(value = "SELECT SUM(pr.price * pp.count) " +
            "FROM store s INNER JOIN product pr on s.id = pr.store_id " +
            "INNER JOIN product_purchase pp on pr.id = pp.product " +
            "INNER JOIN purchase pu on pp.purchase = pu.id " +
            "WHERE s.profile_id = :profileId AND " +
            "pu.purchase_date = CURDATE()", nativeQuery = true)
    Integer getIncomeForDay(@Param("profileId") long profileId);

}
