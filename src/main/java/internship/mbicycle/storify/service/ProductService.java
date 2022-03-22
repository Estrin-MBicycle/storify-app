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

    ProductDTO updateProduct(ProductDTO product, Principal principal);

    void removeProductByStoreIdAndId(ProductDTO productDTO, Principal principal, Long productId);

    void removeAllProductsByStoreId(Principal principal, Long storeId);

    ProductDTO saveProduct(ProductDTO product, Principal principal);

    List<ProductDTO> getAllProducts();

    void changeProductCountAfterThePurchase(PurchaseDTO purchaseDTO);

}
