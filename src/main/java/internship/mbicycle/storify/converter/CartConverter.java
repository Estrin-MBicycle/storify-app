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

@Component
@RequiredArgsConstructor
public class CartConverter {

    private final ProductService productService;

    public CartDTO convertCartToCartDTO(Cart cart) {
        List<ProductDetailInCartDTO> productDetailInCartDTOList = new ArrayList<>();

        int finalCartPrice = cart.getProductsMap().entrySet()
                .stream()
                .map(entry -> {
                    ProductDTO productDTO = productService.getProductById(entry.getKey());
                    ProductDetailInCartDTO productDetailInCartDTO = getProductDetailInCartDto(productDTO, entry.getValue());
                    productDetailInCartDTOList.add(productDetailInCartDTO);
                    return productDTO.getPrice() * entry.getValue();
                })
                .mapToInt(Integer::intValue)
                .sum();

        return getCartDTO(productDetailInCartDTOList, finalCartPrice);
    }

    private CartDTO getCartDTO(List<ProductDetailInCartDTO> productDetailInCartDTOList, Integer finalCartPrice) {
        return CartDTO.builder()
                .productDetailInCartDTOList(productDetailInCartDTOList)
                .sum(finalCartPrice)
                .build();
    }

    private ProductDetailInCartDTO getProductDetailInCartDto(ProductDTO productDTO, Integer amount) {
        return ProductDetailInCartDTO.builder()
                .productId(productDTO.getId())
                .name(productDTO.getProductName())
                .price(productDTO.getPrice())
                .amount(amount)
                .build();
    }
}
