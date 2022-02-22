package internship.mbicycle.storify.service;

import internship.mbicycle.storify.dto.BasketDTO;
import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.model.Basket;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.model.Profile;

import java.util.List;

public interface BasketService {

    void saveProduct(ProductDTO productDTO, Long basket_id);

    void removeProductFromBasket(ProductDTO productDTO, Long basket_id);

    void removeAllProductsFromBasket(Product product, Long basket_id);

    List<BasketDTO> getListOfOrders();
}