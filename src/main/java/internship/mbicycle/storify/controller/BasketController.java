package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.repository.BasketRepository;
import internship.mbicycle.storify.repository.ProfileRepository;
import internship.mbicycle.storify.service.BasketService;
import internship.mbicycle.storify.service.StorifyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequestMapping("/basket")
@RestController
@RequiredArgsConstructor
public class BasketController {

    private final StorifyUserService userService;
    private final BasketService basketService;
    private final ProfileRepository profileRepository;
    private final BasketRepository basketRepository;


    @GetMapping()
    public List<Product> getBasket(Principal principal) {
        StorifyUser storifyUser = userService.getUserByEmail(principal.getName());
        return storifyUser.getProfile().getBasket().getProductList();
    }
}