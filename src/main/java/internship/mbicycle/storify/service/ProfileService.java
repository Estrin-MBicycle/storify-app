package internship.mbicycle.storify.service;

import java.util.List;

import internship.mbicycle.storify.dto.ProfileDTO;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.model.Profile;

public interface ProfileService {

    ProfileDTO getById(long id);

    ProfileDTO updateProfileById(long id, ProfileDTO profileDTO);

    Profile getProfileById(Long id);

    ProfileDTO setFavoriteProductAndSaveProfile(Profile profile, List<Product> products);

    ProfileDTO getByEmail(String email);

    ProfileDTO updateProfileByEmail(String email, ProfileDTO profileDTO);

}
