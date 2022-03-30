package internship.mbicycle.storify.converter;

import internship.mbicycle.storify.dto.CartDTO;
import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.dto.ProductDetailInCartDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CartConverter {
    public CartDTO getCartDTO(List<ProductDetailInCartDTO> productDetailInCartDTOList, Integer finalCartPrice) {
        if (productDetailInCartDTOList.isEmpty()) {
            productDetailInCartDTOList = new ArrayList<>();
            finalCartPrice = 0;
        }
        return CartDTO.builder()
                .productDetailInCartDTOList(productDetailInCartDTOList)
                .sum(finalCartPrice)
                .build();
    }

    public ProductDetailInCartDTO getProductDetailInCartDto(ProductDTO productDTO, Integer quantity) {
        return ProductDetailInCartDTO.builder()
                .productId(productDTO.getId())
                .name(productDTO.getProductName())
                .price(productDTO.getPrice())
                .amount(quantity)
                .build();
    }
}
