package internship.mbicycle.storify.converter;

import internship.mbicycle.storify.dto.ProfileDTO;
import internship.mbicycle.storify.model.Cart;
import internship.mbicycle.storify.model.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileConverter {

    public ProfileDTO convertProfileToProfileDTO(Profile profile) {
        return ProfileDTO.builder()
                .id(profile.getId())
                .name(profile.getName())
                .surname(profile.getSurname())
                .town(profile.getTown())
                .address(profile.getAddress())
                .phone(profile.getPhone())
                .build();
    }

    public Profile convertProfileDTOToProfile(long profileId, Cart cart, ProfileDTO profileDTO) {
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
