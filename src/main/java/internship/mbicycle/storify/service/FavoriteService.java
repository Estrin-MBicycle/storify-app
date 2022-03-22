package internship.mbicycle.storify.service;

import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.dto.ProfileDTO;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.model.Profile;

import java.security.Principal;
import java.util.List;
import java.util.Set;

public interface FavoriteService {

    ProfileDTO removeAllProductFromFavorite(Set<Product> products, Profile profile);

    ProfileDTO removeProductFromFavorite(Long productId, Set<Product> products, Profile profile);

    ProfileDTO addProductToFavorite(Long productId, Set<Product> products, Profile profile);

    List<ProductDTO> getFavoriteByPrincipal(Principal principal);
}
