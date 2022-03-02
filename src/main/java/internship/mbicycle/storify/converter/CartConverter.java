package internship.mbicycle.storify.converter;

import internship.mbicycle.storify.dto.CartDTO;
import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.dto.ProductDetailInCartDTO;
import internship.mbicycle.storify.model.Cart;
import internship.mbicycle.storify.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CartConverter {

    private final ProductService productService;

    public CartDTO convertCartToCartDTO(Cart cart) {
        List<ProductDetailInCartDTO> productDetailInCartDTOList = new ArrayList<>();
        int sum = 0;
        for (Map.Entry<Long, Integer> entry : cart.getProductsMap().entrySet()) {
            ProductDTO productDTO = productService.getProductById(entry.getKey());
            ProductDetailInCartDTO productDetailInCartDTO =
                    ProductDetailInCartDTO.builder()
                            .productId(productDTO.getId())
                            .name(productDTO.getProductName())
                            .price(productDTO.getPrice())
                            .amount(entry.getValue())
                            .build();
            productDetailInCartDTOList.add(productDetailInCartDTO);
            sum += productDTO.getPrice() * entry.getValue();
        }
        return CartDTO.builder()
                .productDetailInCartDTOList(productDetailInCartDTOList)
                .sum(sum)
                .build();
    }
}
