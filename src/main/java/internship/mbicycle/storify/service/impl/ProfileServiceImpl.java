package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.dto.ProfileDTO;
import internship.mbicycle.storify.dto.StoreDTO;
import internship.mbicycle.storify.exception.ErrorCode;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.model.Store;
import internship.mbicycle.storify.repository.ProfileRepository;
import internship.mbicycle.storify.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public ProfileDTO getById(long id) {
        Profile temp = Optional.ofNullable(profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.NOT_FOUND_PROFILE))).get();
        return convertProfileToProfileDTO(temp);
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
        return convertProfileToProfileDTO(result);
    }

    public static ProfileDTO convertProfileToProfileDTO(Profile profile) {
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
        if(profile.getStores() != null) {
            profileDTO.setStores(profile.getStores().stream()
                    .map(StoreServiceImpl::fromStoreToStoreDTO)
                    .collect(Collectors.toList()));
        } else {
            profileDTO.setStores(new ArrayList<StoreDTO>());
        }
        return profileDTO;
    }

    public static Profile convertProfileDTOToProfile(ProfileDTO profileDTO) {
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
        if(profileDTO.getStores() != null) {
            profile.setStores(profileDTO.getStores().stream()
                    .map(StoreServiceImpl::fromStoreDTOToStore)
                    .collect(Collectors.toList()));
        } else {
            profile.setStores(new ArrayList<Store>());
        }
        return profile;
    }
}