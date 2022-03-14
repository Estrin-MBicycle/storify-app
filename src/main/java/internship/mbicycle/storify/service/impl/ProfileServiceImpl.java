package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.converter.ProfileConverter;
import internship.mbicycle.storify.dto.ProfileDTO;
import internship.mbicycle.storify.exception.ProfileNotFoundException;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.repository.ProfileRepository;
import internship.mbicycle.storify.service.ProfileService;
import internship.mbicycle.storify.service.StorifyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static internship.mbicycle.storify.util.ExceptionMessage.NOT_FOUND_PROFILE;
import static java.lang.String.format;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileConverter profileConverter;
    private final StorifyUserService userService;

    @Override
    public Profile getProfileById(Long id) {
        return profileRepository.findById(id).orElseThrow(() ->
                new ProfileNotFoundException(format(NOT_FOUND_PROFILE, id)));
    }

    @Override
    public ProfileDTO setFavoriteProductAndSaveProfile(Set<Product> products, Profile profile) {
        profile.setFavorite(products);
        Profile save = profileRepository.save(profile);
        return profileConverter.convertProfileToProfileDTO(save);
    }

    @Override
    public ProfileDTO getByEmail(String email) {
        StorifyUser user = userService.getUserByEmail(email);
        return profileConverter.convertProfileToProfileDTO(user.getProfile());
    }

    @Override
    public ProfileDTO updateProfileByEmail(String email, ProfileDTO profileDTO) {
        StorifyUser user = userService.getUserByEmail(email);
        Profile updateProfile = profileConverter.convertProfileDTOToProfile(
                user.getProfile().getId(),
                user.getProfile().getCart(),
                profileDTO);
        profileRepository.save(updateProfile);
        return profileDTO;
    }
}