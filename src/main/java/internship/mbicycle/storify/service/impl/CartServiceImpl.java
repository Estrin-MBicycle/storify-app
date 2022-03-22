package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.model.Cart;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.repository.CartRepository;
import internship.mbicycle.storify.service.CartService;
import internship.mbicycle.storify.service.StorifyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final StorifyUserService userService;
    private final CartRepository cartRepository;

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
}
