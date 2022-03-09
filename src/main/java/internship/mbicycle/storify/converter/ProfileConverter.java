package internship.mbicycle.storify.converter;

import internship.mbicycle.storify.dto.ProfileDTO;
import internship.mbicycle.storify.model.Cart;
import internship.mbicycle.storify.model.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileConverter {

    private final CartConverter cartConverter;

    public ProfileDTO convertProfileToProfileDTO(Profile profile) {
        return ProfileDTO.builder()
                .name(profile.getName())
                .surname(profile.getSurname())
                .town(profile.getTown())
                .address(profile.getAddress())
                .phone(profile.getPhone())
                .build();
    }

    public Profile convertProfileDTOToProfile(ProfileDTO profileDTO) {
        return Profile.builder()
                .name(profileDTO.getName())
                .surname(profileDTO.getSurname())
                .town(profileDTO.getTown())
                .address(profileDTO.getAddress())
                .phone(profileDTO.getPhone())
                .build();
    }

    public ProfileDTO convertProfileToProfileDTOShort(Profile profile) {
        return ProfileDTO.builder()
                .name(profile.getName())
                .surname(profile.getSurname())
                .town(profile.getTown())
                .address(profile.getAddress())
                .phone(profile.getPhone())
                .build();
    }

    public Profile convertProfileDTOToProfileShort(Long profileId, Cart cart, ProfileDTO profileDTO) {
        return Profile.builder()
                .id(profileId)
                .name(profileDTO.getName())
                .surname(profileDTO.getSurname())
                .town(profileDTO.getTown())
                .address(profileDTO.getAddress())
                .phone(profileDTO.getPhone())
                .cart(cart)
                .build();
    }
}
