package internship.mbicycle.storify.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import internship.mbicycle.storify.converter.ProductConverter;
import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.dto.ProfileDTO;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.service.FavoriteService;
import internship.mbicycle.storify.service.ProductService;
import internship.mbicycle.storify.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteServiceImpl implements FavoriteService {

    private final ProfileService profileService;
    private final ProductService productService;
    private final ProductConverter productConverter;

    @Override
    public ProfileDTO addProfileToFavoriteProduct(Long productId, Long profileId) {
        Product productDb = productService.getProductById(productId);
        Profile profile = profileService.getProfileById(profileId);
        List<Product> products = profile.getFavorite();
        products.add(productDb);
        return profileService.setFavoriteProductAndSaveProfile(profile, products);
    }

    @Override
    public ProfileDTO removeProfileFromFavoriteProduct(Long productId, Long profileId) {
        Product productDb = productService.getProductById(productId);
        Profile profile = profileService.getProfileById(profileId);
        List<Product> products = profile.getFavorite();
        products.remove(productDb);
        return profileService.setFavoriteProductAndSaveProfile(profile, products);
    }

    @Override
    public List<ProductDTO> getFavoritesProducts(Long id) {
        Profile profile = profileService.getProfileById(id);
        List<Product> products = profile.getFavorite();
        return products.stream().map(productConverter::convertProductToProductDTO)
            .collect(Collectors.toList());
    }
}
