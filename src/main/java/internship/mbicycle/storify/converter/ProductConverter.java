package internship.mbicycle.storify.converter;


import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductConverter {

    private final StoreService storeService;

    public Product convertProductDTOToProduct(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }
        return Product.builder()
            .count(productDTO.getCount())
            .description(productDTO.getDescription())
            .id(productDTO.getId())
            .image(productDTO.getImage())
            .productName(productDTO.getProductName())
            .store(storeService.getStoreById(productDTO.getStoreId()))
            .price(productDTO.getPrice())
            .build();
    }

    public ProductDTO convertProductToProductDTO(Product product) {
        return ProductDTO.builder()
            .id(product.getId())
            .description(product.getDescription())
            .count(product.getCount())
            .image(product.getImage())
            .price(product.getPrice())
            .productName(product.getProductName())
            .storeId(product.getStore().getId())
            .build();
    }
}
