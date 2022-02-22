package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.converter.CartConverter;
import internship.mbicycle.storify.dto.CartDTO;
import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.model.Cart;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.repository.CartRepository;
import internship.mbicycle.storify.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartConverter cartConverter;


    @Override
    public void saveProduct(ProductDTO productDTO, Long basketId) {
        Cart basket = cartRepository.getById(basketId);
        List<Product> productList = basket.getProductList();
        productList.add(ProductServiceImpl.convertToProduct(productDTO));
        basket.setProductList(productList);
    }

    @Override
    public void removeProductFromCart(ProductDTO productDTO, Long basketId) {
        Cart cart = cartRepository.getById(basketId);
        List<Product> productList = cart.getProductList();
        productList.remove(ProductServiceImpl.convertToProduct(productDTO));
        cart.setProductList(productList);
    }

    @Override
    public void removeAllProductsFromCart(Product product, Long basketId) {
        Cart cart = cartRepository.getById(basketId);
        List<Product> productList = cart.getProductList();
        productList.clear();
        cart.setProductList(productList);
    }

    @Override
    public List<CartDTO> getListOfOrders() {
        return cartRepository.findAll().stream()
                .map(cartConverter::convertCartToCartDTO)
                .collect(Collectors.toList());
    }
}
