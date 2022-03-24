package internship.mbicycle.storify.service;

import internship.mbicycle.storify.dto.ProfileDTO;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.model.Profile;

import java.util.Set;

public interface ProfileService {

    Profile getProfileById(Long id);

    ProfileDTO setFavoriteProductAndSaveProfile(Set<Product> products, Profile profile);

    ProfileDTO getByEmail(String email);

    ProfileDTO updateProfileByEmail(String email, ProfileDTO profileDTO);

}
