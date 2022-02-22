package internship.mbicycle.storify.service;

import internship.mbicycle.storify.dto.CartDTO;
import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.model.Product;

import java.util.List;

public interface CartService {

    void saveProduct(ProductDTO productDTO, Long basket_id);

    void removeProductFromCart(ProductDTO productDTO, Long basket_id);

    void removeAllProductsFromCart(Product product, Long basket_id);

    List<CartDTO> getListOfOrders();
}