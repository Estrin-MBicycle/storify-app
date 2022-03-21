package internship.mbicycle.storify.repository;

import internship.mbicycle.storify.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query(nativeQuery = true,
            value = "SELECT SUM(quantity * price)\n" +
                    "FROM product_cart JOIN product\n" +
                    "WHERE product_cart.product_id = product.id and cart_id = ?1 " +
                    "GROUP BY product_id")
    List<Integer> getSumProductInCart(Long id);

    @Query(nativeQuery = true,
            value = "SELECT product_id FROM product_cart WHERE cart_id = ?1")
    List<Long> getIdProductInCart(Long id);

    @Query(nativeQuery = true,
            value = "SELECT quantity FROM product_cart WHERE cart_id = ?1")
    List<Integer> getCountProductInCart(Long id);
}