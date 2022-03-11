package internship.mbicycle.storify.controller;

import java.util.List;

import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.dto.ProfileDTO;
import internship.mbicycle.storify.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ProfileDTO addProfileToFavoriteProduct(@PathVariable Long id,
                                                  @PathVariable Long profileId) {
        return favoriteService.addProfileToFavoriteProduct(id, profileId);
    }

    @DeleteMapping("/{id}/{profileId}")
    public ProfileDTO removeProfileFromFavoriteProduct(@PathVariable Long id,
                                                       @PathVariable Long profileId) {
        return favoriteService.removeProfileFromFavoriteProduct(id, profileId);
    }

    @GetMapping("/{profileId}")
    public List<ProductDTO> getFavoritesProducts(@PathVariable Long profileId) {
        return favoriteService.getFavoritesProducts(profileId);
    }
}
