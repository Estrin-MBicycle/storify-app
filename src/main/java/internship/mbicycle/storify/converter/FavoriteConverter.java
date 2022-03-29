package internship.mbicycle.storify.converter;

import internship.mbicycle.storify.dto.FavoriteDTO;
import internship.mbicycle.storify.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FavoriteConverter {
    public FavoriteDTO convertProductToFavoriteDTO(Product product) {
        boolean availability = product.getCount() > 0;
        return FavoriteDTO.builder()
                .productId(product.getId())
                .name(product.getProductName())
                .price(product.getPrice())
                .status(availability)
                .build();
    }
}