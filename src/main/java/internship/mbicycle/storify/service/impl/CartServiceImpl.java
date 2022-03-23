package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.converter.CartConverter;
import internship.mbicycle.storify.dto.CartDTO;
import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.dto.ProductDetailInCartDTO;
import internship.mbicycle.storify.model.Cart;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.repository.CartRepository;
import internship.mbicycle.storify.service.CartService;
import internship.mbicycle.storify.service.ProductService;
import internship.mbicycle.storify.service.StorifyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final StorifyUserService userService;
    private final CartRepository cartRepository;
    private final CartConverter cartConverter;
    private final ProductService productService;

    @Override
    public Cart getCartByPrincipal(@ApiIgnore Principal principal) {
        StorifyUser storifyUser = userService.getUserByEmail(principal.getName());
        return storifyUser.getProfile().getCart();
    }

    @Override
    public void deleteProduct(Cart cart, long productId) {
        cart.getProductsMap().remove(productId);
        cartRepository.save(cart);
    }

    @Override
    public void changeProductByCount(Cart cart, long productId, int count) {
        cart.getProductsMap().replace(productId, count);
        cartRepository.save(cart);
    }

    @Override
    public void deleteAllProduct(Cart cart) {
        cart.getProductsMap().clear();
        cartRepository.save(cart);
    }

    @Override
    public void addProduct(Cart cart, long productId, int count) {
        cart.getProductsMap().put(productId, count);
        cartRepository.save(cart);
    }

    @Override
    public CartDTO getListProductsInCart(Cart cart) {
        Integer finalCartPrice = cartRepository.getSumProductInCart(cart.getId());
        List<ProductDetailInCartDTO>  productDetailInCartDTOList = cart.getProductsMap().entrySet()
                .stream()
                .map(entry -> {
                    ProductDTO productDTO = productService.getProductDTOById(entry.getKey());
                    return cartConverter.getProductDetailInCartDto(productDTO, entry.getValue());
                }).collect(Collectors.toList());
        return cartConverter.getCartDTO(productDetailInCartDTOList, finalCartPrice);
    }
}
