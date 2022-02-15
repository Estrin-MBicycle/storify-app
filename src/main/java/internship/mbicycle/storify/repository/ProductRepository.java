package internship.mbicycle.storify.repository;

import internship.mbicycle.storify.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findProductByProductName(String name);

    void removeById(Long id);

    void removeByStoreIdAndId(Long storeId,Long productId);

    void removeAllByStoreId(Long storeId);

    List<Product> findAllByStoreId(Long storeId);

}
