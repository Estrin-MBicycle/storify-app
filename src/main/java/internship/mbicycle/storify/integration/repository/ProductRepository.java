package internship.mbicycle.storify.integration.repository;

import internship.mbicycle.storify.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findProductByProductName(String name);

    void removeProductById(Long id);

    void removeProductByStoreIdAndId(Long storeId, Long productId);

    void removeAllByStoreId(Long storeId);

    List<Product> findAllByStoreId(Long storeId);

}
