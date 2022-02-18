package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.converter.ProfileConverter;
import internship.mbicycle.storify.dto.ProfileDTO;
import internship.mbicycle.storify.exception.ProfileNotFoundException;
import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.repository.ProfileRepository;
import internship.mbicycle.storify.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static internship.mbicycle.storify.util.ExceptionMessage.NOT_FOUND_PROFILE;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileConverter profileConverter;

    @Override
    public ProfileDTO getById(long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(String.format(NOT_FOUND_PROFILE, id)));
        return profileConverter.convertProfileToProfileDTO(profile);
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
        return profileConverter.convertProfileToProfileDTO(result);
    }
}