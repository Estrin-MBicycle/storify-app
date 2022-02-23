package internship.mbicycle.storify.converter;

import internship.mbicycle.storify.dto.CartDTO;
import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.model.Cart;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartConverter {

    private final ProfileConverter profileConverter;
    private final ProductConverter productConverter;

    public CartDTO convertCartToCartDTO(Cart cart) {
        List<ProductDTO> productDTOList = cart.getProductList().stream()
                .map(productConverter::convertProductToProductDTO)
                .collect(Collectors.toList());

        return CartDTO.builder()
                .id(cart.getId())
                .productList(productDTOList)
                .build();
    }

    public Cart convertCartDTOToCart(CartDTO cartDTO) {
        if (cartDTO == null) {
            return null;
        }
        List<Product> productList = cartDTO.getProductList()
                .stream()
                .map(productConverter::convertProductDTOToProduct)
                .collect(Collectors.toList());

        return Cart.builder()
                .id(cartDTO.getId())
                .productList(productList)
                .build();
    }
}
