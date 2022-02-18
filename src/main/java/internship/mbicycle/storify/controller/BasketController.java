package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.dto.BasketDTO;
import internship.mbicycle.storify.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/baskets")
@RestController
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @GetMapping("/{id}")
    public BasketDTO getBasketById(@PathVariable Long id) {
        return basketService.getBasket(id);
    }

}
