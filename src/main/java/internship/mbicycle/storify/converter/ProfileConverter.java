package internship.mbicycle.storify.converter;

import internship.mbicycle.storify.dto.ProfileDTO;
import internship.mbicycle.storify.model.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileConverter {

    public ProfileDTO convertProfileToProfileDTO(Profile profile) {
        if (profile == null) {
            return null;
        }
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(profile.getId());
        profileDTO.setName(profile.getName());
        profileDTO.setSurname(profile.getSurname());
        profileDTO.setTown(profile.getTown());
        profileDTO.setAddress(profile.getAddress());
        profileDTO.setPhone(profile.getPhone());
        return profileDTO;
    }

    public Profile convertProfileDTOToProfile(ProfileDTO profileDTO) {
        if (profileDTO == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setId(profileDTO.getId());
        profile.setName(profileDTO.getName());
        profile.setSurname(profileDTO.getSurname());
        profile.setTown(profileDTO.getTown());
        profile.setAddress(profileDTO.getAddress());
        profile.setPhone(profileDTO.getPhone());
        return profile;
    }
}
