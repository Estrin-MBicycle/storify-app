package internship.mbicycle.storify.converter;

import internship.mbicycle.storify.dto.ProfileDTO;
import internship.mbicycle.storify.model.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileConverter {

    private final CartConverter cartConverter;

    public ProfileDTO convertProfileToProfileDTO(Profile profile) {
        return ProfileDTO.builder()
                .id(profile.getId())
                .name(profile.getName())
                .surname(profile.getSurname())
                .town(profile.getTown())
                .address(profile.getAddress())
                .phone(profile.getPhone())
                .cartDTO(cartConverter.convertCartToCartDTO(profile.getCart()))
                .build();
    }

    public Profile convertProfileDTOToProfile(ProfileDTO profileDTO) {
        return Profile.builder()
                .id(profileDTO.getId())
                .name(profileDTO.getName())
                .surname(profileDTO.getSurname())
                .town(profileDTO.getTown())
                .address(profileDTO.getAddress())
                .phone(profileDTO.getPhone())
                .build();
    }
}
