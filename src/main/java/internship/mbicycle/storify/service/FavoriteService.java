package internship.mbicycle.storify.service;

import internship.mbicycle.storify.dto.ProductDTO;

public interface FavoriteService {

    ProductDTO addProfileToFavoriteProduct(Long productId, Long profileId);

    ProductDTO removeProfileFromFavoriteProduct(Long productId, Long profileId);
}
