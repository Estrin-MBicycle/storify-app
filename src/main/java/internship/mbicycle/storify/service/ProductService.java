package internship.mbicycle.storify.service;

import java.util.List;

import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.model.Profile;

public interface ProductService {

    ProductDTO getProductDTOById(Long id);

    ProductDTO setProfilesAndSaveProduct(Product product, List<Profile> profiles);

    Product getProductById(Long id);

    List<ProductDTO> getAllProductsFromStore(Long storeId);

    ProductDTO updateProduct(ProductDTO product, Long id, Long storeId);

    void removeProductByStoreIdAndId(Long storeId, Long productId);

    void removeAllProductsByStoreId(Long storeId);

    ProductDTO saveProduct(ProductDTO product, Long storeId);

    List<ProductDTO> getAllProducts();

}
