package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.repository.CartRepository;
import internship.mbicycle.storify.repository.ProfileRepository;
import internship.mbicycle.storify.service.CartService;
import internship.mbicycle.storify.service.StorifyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequestMapping("/user/cart")
@RestController
@RequiredArgsConstructor
public class CartController {

    private final StorifyUserService userService;
    private final CartService cartService;
    private final ProfileRepository profileRepository;
    private final CartRepository cartRepository;


    @GetMapping()
    public List<Product> getCart(Principal principal) {
        StorifyUser storifyUser = userService.getUserByEmail(principal.getName());
        return storifyUser.getProfile().getCart().getProductList();
    }
}