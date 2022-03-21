package internship.mbicycle.storify.converter;

import internship.mbicycle.storify.dto.CartDTO;
import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.dto.ProductDetailInCartDTO;
import internship.mbicycle.storify.model.Cart;
import internship.mbicycle.storify.repository.CartRepository;
import internship.mbicycle.storify.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class CartConverter {

    private final ProductService productService;
    private final CartRepository cartRepository;

    public CartDTO convertCartToCartDTO(Cart cart) {
        List<ProductDetailInCartDTO> productDetailInCartDTOList = new ArrayList<>();
        List<Integer> listSumProductsInCart = cartRepository.getSumProductInCart(cart.getId());
        List<Integer> quantityList = cartRepository.getCountProductInCart(cart.getId());
        List<Long> listIdProduct = cartRepository.getIdProductInCart(cart.getId());
        Integer finalCartPrice = listSumProductsInCart.stream().mapToInt(Integer::intValue).sum();

        IntStream.range(0, listIdProduct.size()).forEach(i -> {
            ProductDTO productDTO = productService.getProductDTOById(listIdProduct.get(i));
            ProductDetailInCartDTO productDetailInCartDTO = getProductDetailInCartDto(productDTO, quantityList.get(i));
            productDetailInCartDTOList.add(productDetailInCartDTO);
        });

        return getCartDTO(productDetailInCartDTOList, finalCartPrice);
    }

    private CartDTO getCartDTO(List<ProductDetailInCartDTO> productDetailInCartDTOList, Integer finalCartPrice) {
        return CartDTO.builder()
                .productDetailInCartDTOList(productDetailInCartDTOList)
                .sum(finalCartPrice)
                .build();
    }

    private ProductDetailInCartDTO getProductDetailInCartDto(ProductDTO productDTO, Integer quantity) {
        return ProductDetailInCartDTO.builder()
                .productId(productDTO.getId())
                .name(productDTO.getProductName())
                .price(productDTO.getPrice())
                .amount(quantity)
                .build();
    }
}