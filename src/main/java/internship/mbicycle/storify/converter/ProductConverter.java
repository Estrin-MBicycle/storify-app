package internship.mbicycle.storify.converter;


import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {

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
                .price(productDTO.getPrice())
                .storeId(productDTO.getStoreId())
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
                .storeId(product.getStoreId())
                .build();
    }
}
