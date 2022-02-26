package internship.mbicycle.storify.service;

import internship.mbicycle.storify.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO getProductById(Long id);

    ProductDTO getProductByName(String name);

    void removeProductById(Long id);

    List<ProductDTO> getAllProductsFromStore(Long storeId);

    ProductDTO updateProduct(ProductDTO product, Long id, Long storeId);

    void removeProductByStoreIdAndId(Long storeId, Long productId);

    void removeAllProductsByStoreId(Long storeId);

    ProductDTO saveProduct(ProductDTO product, Long storeId);

    List<ProductDTO> getAllProducts();

}
