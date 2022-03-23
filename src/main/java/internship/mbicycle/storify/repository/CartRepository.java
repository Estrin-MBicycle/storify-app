package internship.mbicycle.storify.repository;

import internship.mbicycle.storify.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query(nativeQuery = true,
            value = "SELECT SUM(quantity * price) FROM product_cart JOIN product " +
                    "WHERE product_cart.product_id = product.id and cart_id = ?1")
    Integer getSumProductInCart(Long id);
}