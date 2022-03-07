package internship.mbicycle.storify.service;

import internship.mbicycle.storify.dto.ProfileDTO;
import internship.mbicycle.storify.model.Profile;

public interface ProfileService {

    ProfileDTO getById(long id);

    ProfileDTO updateProfile(long id, ProfileDTO profileDTO);

    Profile getProfileById(Long id);
}
