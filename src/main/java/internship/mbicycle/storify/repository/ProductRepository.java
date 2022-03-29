package internship.mbicycle.storify.repository;

import internship.mbicycle.storify.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findProductByProductName(String name);

    void removeProductById(Long id);

    void removeProductByStoreIdAndId(Long storeId, Long productId);

    void removeAllByStoreId(Long storeId);

    List<Product> findAllByStoreId(Long storeId);

    @Query(value = "UPDATE product " +
            "SET deleted = true " +
            "WHERE id = :product_id AND id IN (" +
            "SELECT product " +
            "FROM product_purchase)", nativeQuery = true)
    void setDeleteState(@Param("product_id") long product_id);

    @Query(value = "UPDATE product " +
            "SET deleted = true " +
            "WHERE store_id = :store_id AND id IN (" +
            "SELECT product " +
            "FROM product_purchase)", nativeQuery = true)
    void setDeleteStateByStoreId(@Param("store_id") long store_id);

}
