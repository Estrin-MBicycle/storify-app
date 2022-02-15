package internship.mbicycle.storify.service;

import internship.mbicycle.storify.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO findById(Long id);

    ProductDTO findByName(String name);

    List<ProductDTO> findAllProductsFromStore(Long storeId);

    void removeProduct(Long id);

    void removeProductByStoreIdAndId(Long storeId,Long productId);

    void removeAllProductByStoreId(Long storeId);

    ProductDTO saveProduct(ProductDTO product);

    List<ProductDTO> getAllProduct();

}
