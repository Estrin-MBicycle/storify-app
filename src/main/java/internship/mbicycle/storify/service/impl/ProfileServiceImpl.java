package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.dto.ProfileDTO;
import internship.mbicycle.storify.exception.ErrorCode;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.repository.ProfileRepository;
import internship.mbicycle.storify.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public ProfileDTO getById(long id) {
        Profile temp = Optional.ofNullable(profileRepository.findById(id))
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.NOT_FOUND_PROFILE)).get();
        return convertEntityToDTO(temp);
    }

    @Override
    public ProfileDTO updateProfile(long id, ProfileDTO profileDTO) {
        Profile temp = profileRepository.findById(id).get();
        temp.setName(profileDTO.getName());
        temp.setSurname(profileDTO.getSurname());
        temp.setTown(profileDTO.getTown());
        temp.setAddress(profileDTO.getAddress());
        temp.setPhone(profileDTO.getPhone());
        Profile result = profileRepository.save(temp);
        return convertEntityToDTO(result);
    }

    public static ProfileDTO convertEntityToDTO(Profile profile) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(profile.getId());
        profileDTO.setName(profile.getName());
        profileDTO.setSurname(profile.getSurname());
        profileDTO.setTown(profile.getTown());
        profileDTO.setAddress(profile.getAddress());
        profileDTO.setPhone(profile.getPhone());
//        need method
//        for(int i = 0; i < profile.getStores().size(); i++) {
//            profileDTO.getStores().add(
//                    StoreServiceImpl.convertEntityToDTO(
//                            profile.getStores().get(i)));
//        }
        return profileDTO;
    }

    public static Profile convertDTOToEntity(ProfileDTO profileDTO) {
        Profile profile = new Profile();
        profile.setId(profileDTO.getId());
        profile.setName(profileDTO.getName());
        profile.setSurname(profileDTO.getSurname());
        profile.setTown(profileDTO.getTown());
        profile.setAddress(profileDTO.getAddress());
        profile.setPhone(profileDTO.getPhone());
//        need method
//        for(int i = 0; i < profileDTO.getStores().size(); i++) {
//            profile.getStores().add(
//                    StoreServiceImpl.convertDTOToEntity(
//                            profileDTO.getStores().get(i)));
//        }
        return profile;
    }
}