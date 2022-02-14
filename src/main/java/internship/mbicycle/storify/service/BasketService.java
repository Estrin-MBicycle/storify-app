package internship.mbicycle.storify.service;

public interface BasketService {

    BasketDTO getBasket(Long userId);

    void addProductToBasket(ProductDTO product, Long userId);

    void removeProduct(ProductDTO product, Long userId);

    void removeAllProducts(Long userId);

}