package internship.mbicycle.storify.service;

import internship.mbicycle.storify.dto.ProductDTO;

public interface ProductService {

    ProductDTO findById(Long id);

    ProductDTO findByName(String name);

    void removeProduct(Long id);

    void removeProductByStoreIdAndId(Long storeId,Long productId);

    void removeAllProductByStoreId(Long storeId);

    ProductDTO saveProduct(ProductDTO product);


}
