package internship.mbicycle.storify.service;

import java.util.List;

import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.dto.ProfileDTO;

public interface FavoriteService {

    ProfileDTO addProfileToFavoriteProduct(Long productId, Long profileId);

    ProfileDTO removeProfileFromFavoriteProduct(Long productId, Long profileId);

    List<ProductDTO> getFavoritesProducts(Long id);
}
