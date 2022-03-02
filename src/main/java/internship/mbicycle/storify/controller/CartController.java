package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.converter.CartConverter;
import internship.mbicycle.storify.dto.CartDTO;
import internship.mbicycle.storify.model.Cart;
import internship.mbicycle.storify.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/user/cart")
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping()
    public CartDTO getCart(Principal principal) {
        Cart cart = cartService.getCartByPrincipal(principal);
        return cartConverter.convertCartToCartDTO(cart);
    }

    @DeleteMapping("/{id}")
    public CartDTO deleteProduct(@PathVariable(name = "id") long productId,
                                 Principal principal) {
        Cart cart = cartService.getCartByPrincipal(principal);
        cartService.deleteProduct(cart, productId);
        return cartConverter.convertCartToCartDTO(cart);
    }

    @PutMapping("/{id}/{count}")
    public CartDTO deleteProductByCount(@PathVariable(name = "id") long productId,
                                        @PathVariable(name = "count") int count,
                                        Principal principal) {
        Cart cart = cartService.getCartByPrincipal(principal);
        cartService.changeProductByCount(cart, productId, count);
        return cartConverter.convertCartToCartDTO(cart);
    }

    @DeleteMapping()
    public CartDTO deleteAllProduct(Principal principal) {
        Cart cart = cartService.getCartByPrincipal(principal);
        cartService.deleteAllProduct(cart);
        return cartConverter.convertCartToCartDTO(cart);
    }

    @PostMapping("/{id}/{count}")
    public void addProductToCart(@PathVariable(name = "id") long productId,
                                 @PathVariable(name = "count") int count,
                                 Principal principal) {
        Cart cart = cartService.getCartByPrincipal(principal);
        cartService.addProduct(cart, productId, count);
    }
}