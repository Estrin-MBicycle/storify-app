package internship.mbicycle.storify.service;

import java.security.Principal;
import java.util.List;

import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.dto.PurchaseDTO;
import internship.mbicycle.storify.model.Product;

public interface ProductService {

    ProductDTO getProductDTOById(Long id);

    Product getProductById(Long id);

    List<ProductDTO> getAllProductsFromStore(Long storeId);

    ProductDTO updateProduct(ProductDTO product, Long id, Long storeId, Principal principal);

    void removeProductByStoreIdAndId(Long storeId, Long productId, Principal principal);

    void removeAllProductsByStoreId(Principal principal, Long storeId);

    ProductDTO saveProduct(ProductDTO product, Long storeId, Principal principal);

    List<ProductDTO> getAllProducts();

    void changeProductCountAfterThePurchase(PurchaseDTO purchaseDTO);

}
