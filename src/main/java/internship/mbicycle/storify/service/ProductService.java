package internship.mbicycle.storify.service;

import internship.mbicycle.storify.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO getProductById(Long id);

    ProductDTO getProductByName(String name);

    List<ProductDTO> getAllProductsFromStore(Long storeId);

    void removeProductById(Long id);

    void removeProductByStoreIdAndId(Long storeId, Long productId);

    void removeAllProductsByStoreId(Long storeId);

    ProductDTO saveProduct(ProductDTO product);

    List<ProductDTO> getAllProducts();

}
