package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PutMapping("/{id}/{profileId}")
    public ProductDTO addProfileToFavoriteProduct(@PathVariable Long id,
                                                  @PathVariable Long profileId) {
        return favoriteService.addProfileToFavoriteProduct(id, profileId);
    }

    @DeleteMapping("/{id}/{profileId}")
    public ProductDTO removeProfileFromFavoriteProduct(@PathVariable Long id,
                                                       @PathVariable Long profileId) {
        return favoriteService.removeProfileFromFavoriteProduct(id, profileId);
    }
}
