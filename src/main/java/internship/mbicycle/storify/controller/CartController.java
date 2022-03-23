package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.dto.CartDTO;
import internship.mbicycle.storify.dto.PurchaseDTO;
import internship.mbicycle.storify.model.Cart;
import internship.mbicycle.storify.service.CartService;
import internship.mbicycle.storify.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;

@RequestMapping("/user/cart")
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final PurchaseService purchaseService;

    @GetMapping
    public CartDTO getProductsInCart(@ApiIgnore Principal principal) {
        Cart cart = cartService.getCartByPrincipal(principal);
        return cartService.getListProductsInCart(cart);
    }

    @DeleteMapping("/{id}")
    public CartDTO deleteProductFromCart(@PathVariable(name = "id") long productId,
                                         @ApiIgnore Principal principal) {
        Cart cart = cartService.getCartByPrincipal(principal);
        cartService.deleteProduct(cart, productId);
        return cartService.getListProductsInCart(cart);
    }

    @PutMapping("/{id}/{count}")
    public CartDTO deleteProductByCountFromCart(@PathVariable(name = "id") long productId,
                                                @PathVariable(name = "count") int count,
                                                @ApiIgnore Principal principal) {
        Cart cart = cartService.getCartByPrincipal(principal);
        cartService.changeProductByCount(cart, productId, count);
        return cartService.getListProductsInCart(cart);
    }

    @DeleteMapping
    public CartDTO deleteAllProductsFromCart(@ApiIgnore Principal principal) {
        Cart cart = cartService.getCartByPrincipal(principal);
        cartService.deleteAllProduct(cart);
        return cartService.getListProductsInCart(cart);
    }

    @PostMapping("/{id}/{count}")
    public void addProductToCart(@PathVariable(name = "id") long productId,
                                 @PathVariable(name = "count") int count,
                                 @ApiIgnore Principal principal) {
        Cart cart = cartService.getCartByPrincipal(principal);
        cartService.addProduct(cart, productId, count);
    }

    @GetMapping("/getAllPurchase")
    public List<PurchaseDTO> getAllPurchasesByProfile(Principal principal) {
        return purchaseService.getAllPurchasesByProfile(principal);
    }
}