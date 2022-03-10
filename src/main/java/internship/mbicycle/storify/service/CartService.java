package internship.mbicycle.storify.service;

import internship.mbicycle.storify.model.Cart;

import java.security.Principal;

public interface CartService {

    Cart getCartByPrincipal(Principal principal);

    void deleteProduct(Cart cart, long productId);

    void deleteAllProduct(Cart cart);

    void changeProductByCount(Cart cart, long productId, int count);

    void addProduct(Cart cart, long productId, int count);
}