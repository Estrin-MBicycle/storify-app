package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.converter.BasketConverter;
import internship.mbicycle.storify.dto.BasketDTO;
import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.model.Basket;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.repository.BasketRepository;
import internship.mbicycle.storify.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static internship.mbicycle.storify.util.ExceptionMessage.NOT_FOUND_BASKET;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final BasketConverter basketConverter;


    @Override
    public void saveProduct(ProductDTO productDTO, Long basketId) {
        Basket basket = basketRepository.getById(basketId);
        List<Product> productList = basket.getProductList();
        productList.add(ProductServiceImpl.convertToProduct(productDTO));
        basket.setProductList(productList);
    }

    @Override
    public void removeProductFromBasket(ProductDTO productDTO, Long basketId) {
        Basket basket = basketRepository.getById(basketId);
        List<Product> productList = basket.getProductList();
        productList.remove(ProductServiceImpl.convertToProduct(productDTO));
        basket.setProductList(productList);
    }

    @Override
    public void removeAllProductsFromBasket(Product product, Long basketId) {
        Basket basket = basketRepository.getById(basketId);
        List<Product> productList = basket.getProductList();
        productList.clear();
        basket.setProductList(productList);

    }

    @Override
    public List<BasketDTO> getListOfOrders() {
        return basketRepository.findAll().stream()
                .map(basketConverter::convertBasketToBasketDTO)
                .collect(Collectors.toList());
    }
}
