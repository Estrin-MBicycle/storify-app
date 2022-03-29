package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.dto.FavoriteDTO;
import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.dto.ProfileDTO;
import internship.mbicycle.storify.model.Cart;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.service.CartService;
import internship.mbicycle.storify.service.FavoriteService;
import internship.mbicycle.storify.service.StorifyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final StorifyUserService userService;
    private final CartService cartService;

    @PatchMapping("/{id}")
    public ProfileDTO addProductByFavorite(@PathVariable Long id,
                                           @ApiIgnore Principal principal) {
        return favoriteService.addProductToFavorite(id, getProductsFromFavorite(principal), getProfile(principal));
    }
    @PostMapping("/{id}")
    public void addProductFromFavoriteToCart(@PathVariable(name = "id") long productId,
                                 @ApiIgnore Principal principal) {
        Cart cart = cartService.getCartByPrincipal(principal);
        cartService.addProduct(cart, productId, 1);
        favoriteService.removeProductFromFavorite(productId,getProductsFromFavorite(principal),getProfile(principal));
    }

    @DeleteMapping("/{id}")
    public ProfileDTO removeProductFromFavorite(@PathVariable Long id,
                                                @ApiIgnore Principal principal) {
        return favoriteService.removeProductFromFavorite(id, getProductsFromFavorite(principal), getProfile(principal));
    }

    @DeleteMapping()
    public ProfileDTO removeAllProductsFromFavorite(@ApiIgnore Principal principal) {
        return favoriteService.removeAllProductFromFavorite(getProductsFromFavorite(principal), getProfile(principal));
    }

    @GetMapping()
    public List<FavoriteDTO> getFavorite(@ApiIgnore Principal principal) {
        return favoriteService.getFavoriteByPrincipal(principal);
    }

    private Set<Product> getProductsFromFavorite(@ApiIgnore Principal principal) {
        return userService.getUserByEmail(principal.getName()).getProfile().getFavorite();
    }

    private Profile getProfile(Principal principal) {
        return userService.getUserByEmail(principal.getName()).getProfile();
    }
}

