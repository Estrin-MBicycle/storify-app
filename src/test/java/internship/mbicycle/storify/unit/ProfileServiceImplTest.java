package internship.mbicycle.storify.unit;

import internship.mbicycle.storify.converter.ProfileConverter;
import internship.mbicycle.storify.dto.ProfileDTO;
import internship.mbicycle.storify.exception.ProfileNotFoundException;
import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.integration.repository.ProfileRepository;
import internship.mbicycle.storify.service.impl.ProfileServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static internship.mbicycle.storify.util.ExceptionMessage.NOT_FOUND_PROFILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceImplTest {

    public static final long PROFILE_ID = 1L;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private ProfileConverter profileConverter;

    @InjectMocks
    private ProfileServiceImpl profileService;

    @Test
    void shouldFindProfileById() {
        given(profileRepository.findById(PROFILE_ID)).willReturn(Optional.empty());

        ProfileNotFoundException exception = assertThrows(ProfileNotFoundException.class, () -> profileService.getById(PROFILE_ID));

        assertEquals(String.format(NOT_FOUND_PROFILE, PROFILE_ID), exception.getMessage());

        then(profileRepository).should(only()).findById(PROFILE_ID);
        then(profileConverter).shouldHaveNoInteractions();
    }

    @Test
    void shouldUpdateProfile() {
        Profile profile = Profile.builder().id(PROFILE_ID).build();
        ProfileDTO profileDTO = ProfileDTO.builder().id(PROFILE_ID).build();
        ProfileDTO expected = ProfileDTO.builder().id(PROFILE_ID).build();

        given(profileRepository.getById(PROFILE_ID)).willReturn(profile);
        given(profileRepository.save(profile)).willReturn(profile);
        given(profileConverter.convertProfileToProfileDTO(profile)).willReturn(expected);

        ProfileDTO actual = profileService.updateProfile(PROFILE_ID, profileDTO);

        assertEquals(expected, actual);

        then(profileRepository).should(times(1)).getById(PROFILE_ID);
        then(profileRepository).should(times(1)).save(profile);
        then(profileRepository).shouldHaveNoMoreInteractions();
        then(profileConverter).should(only()).convertProfileToProfileDTO(profile);
    }
}
