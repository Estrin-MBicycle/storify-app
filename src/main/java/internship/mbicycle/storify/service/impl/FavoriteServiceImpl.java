package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.converter.FavoriteConverter;
import internship.mbicycle.storify.converter.ProductConverter;
import internship.mbicycle.storify.dto.FavoriteDTO;
import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.dto.ProfileDTO;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.service.FavoriteService;
import internship.mbicycle.storify.service.ProductService;
import internship.mbicycle.storify.service.ProfileService;
import internship.mbicycle.storify.service.StorifyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteServiceImpl implements FavoriteService {

    private final ProfileService profileService;
    private final ProductService productService;
    private final FavoriteConverter favoriteConverter;
    private final StorifyUserService userService;

    @Override
    public ProfileDTO addProductToFavorite(Long productId, Set<Product> products, Profile profile) {
        Product productDb = productService.getProductById(productId);
        products.add(productDb);
        return profileService.setFavoriteProductAndSaveProfile(products, profile);
    }

    @Override
    public ProfileDTO removeProductFromFavorite(Long productId, Set<Product> products, Profile profile) {
        Product productDb = productService.getProductById(productId);
        products.remove(productDb);
        return profileService.setFavoriteProductAndSaveProfile(products, profile);
    }

    @Override
    public ProfileDTO removeAllProductFromFavorite(Set<Product> products, Profile profile) {
        products.clear();
        return profileService.setFavoriteProductAndSaveProfile(products, profile);
    }

    @Override
    public List<FavoriteDTO> getFavoriteByPrincipal(Principal principal) {
        Set<Product> products = userService.getUserByEmail(principal.getName()).getProfile().getFavorite();
        return products.stream().map(favoriteConverter::convertProductToFavoriteDTO)
                .collect(Collectors.toList());
    }
}
