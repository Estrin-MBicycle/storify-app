package internship.mbicycle.storify.converter;

import internship.mbicycle.storify.dto.BasketDTO;
import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.model.Basket;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BasketConverter {

    private final ProfileConverter profileConverter;

    public BasketDTO convertBasketToBasketDTO(Basket basket) {
        List<ProductDTO> productDTOList = basket.getProductList().stream()
                .map(ProductServiceImpl::convertToDTO)
                .collect(Collectors.toList());

        return BasketDTO.builder()
                .id(basket.getId())
                .productList(productDTOList)
                .build();
    }

    public Basket convertBasketDTOToBasket(BasketDTO basketDTO) {
        if (basketDTO == null) {
            return null;
        }
        List<Product> productList = basketDTO.getProductList()
                .stream()
                .map(ProductServiceImpl::convertToProduct)
                .collect(Collectors.toList());

        return Basket.builder()
                .id(basketDTO.getId())
                .productList(productList)
                .build();
    }
}
