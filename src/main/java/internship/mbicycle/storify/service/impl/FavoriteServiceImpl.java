package internship.mbicycle.storify.service.impl;

import java.util.List;

import internship.mbicycle.storify.dto.ProductDTO;
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

    @Override
    public ProductDTO addProfileToFavoriteProduct(Long productId, Long profileId) {
        Product productDb = productService.getProductById(productId);
        Profile profile = profileService.getProfileById(profileId);
        List<Profile> profiles = productDb.getProfiles();
        profiles.add(profile);
        return productService.setProfilesAndSaveProduct(productDb, profiles);
    }

    @Override
    public ProductDTO removeProfileFromFavoriteProduct(Long productId, Long profileId) {
        Product productDb = productService.getProductById(productId);
        Profile profile = profileService.getProfileById(profileId);
        List<Profile> profiles = productDb.getProfiles();
        profiles.remove(profile);
        return productService.setProfilesAndSaveProduct(productDb, profiles);
    }
}
