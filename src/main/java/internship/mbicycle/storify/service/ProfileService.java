package internship.mbicycle.storify.service;

import internship.mbicycle.storify.dto.ProfileDTO;

public interface ProfileService {

    ProfileDTO getById(long id);
    ProfileDTO update(long id, ProfileDTO profileDTO);
}
