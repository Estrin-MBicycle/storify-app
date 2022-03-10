package internship.mbicycle.storify.unit;

import internship.mbicycle.storify.converter.ProfileConverter;
import internship.mbicycle.storify.dto.ProfileDTO;
import internship.mbicycle.storify.exception.ProfileNotFoundException;
import internship.mbicycle.storify.model.Cart;
import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.repository.ProfileRepository;
import internship.mbicycle.storify.service.StorifyUserService;
import internship.mbicycle.storify.service.impl.ProfileServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
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
    private StorifyUserService userService;

    @Mock
    private ProfileConverter profileConverter;

    @InjectMocks
    private ProfileServiceImpl profileService;

    @Test
    void shouldFindProfileById() {
        given(profileRepository.findById(PROFILE_ID)).willReturn(Optional.empty());

        ProfileNotFoundException exception = assertThrows(ProfileNotFoundException.class, () -> profileService.getProfileById(PROFILE_ID));

        assertEquals(String.format(NOT_FOUND_PROFILE, PROFILE_ID), exception.getMessage());

        then(profileRepository).should(only()).findById(PROFILE_ID);
        then(profileConverter).shouldHaveNoInteractions();
        then(userService).shouldHaveNoInteractions();
    }

    @Test
    void shouldSetFavoriteProductAndSaveProfile() {
        Profile profile = Profile.builder().id(PROFILE_ID).build();
        ProfileDTO expected = ProfileDTO.builder().id(PROFILE_ID).build();

        given(profileRepository.save(profile)).willReturn(profile);
        given(profileConverter.convertProfileToProfileDTO(profile)).willReturn(expected);

        ProfileDTO actual = profileService.setFavoriteProductAndSaveProfile(profile, Collections.emptyList());

        assertEquals(expected, actual);

        then(profileRepository).should(only()).save(profile);
        then(profileConverter).should(only()).convertProfileToProfileDTO(profile);
        then(userService).shouldHaveNoInteractions();
    }

    @Test
    void shouldGetByEmail() {
        Profile profile = Profile.builder().id(PROFILE_ID).build();
        StorifyUser storifyUser = StorifyUser.builder().id(1L).profile(profile).build();
        ProfileDTO expected = ProfileDTO.builder().id(PROFILE_ID).build();

        given(userService.getUserByEmail("m@mail.com")).willReturn(storifyUser);
        given(profileConverter.convertProfileToProfileDTO(profile)).willReturn(expected);

        ProfileDTO actual = profileService.getByEmail("m@mail.com");

        assertEquals(expected, actual);

        then(profileRepository).shouldHaveNoInteractions();
        then(profileConverter).should(only()).convertProfileToProfileDTO(profile);
        then(userService).should(only()).getUserByEmail("m@mail.com");
    }

    @Test
    void shouldUpdateProfile() {
        Cart cart = Cart.builder().id(1L).build();
        Profile profile = Profile.builder().id(PROFILE_ID).cart(cart).build();
        StorifyUser storifyUser = StorifyUser.builder().id(1L).profile(profile).build();
        ProfileDTO profileDTO = ProfileDTO.builder().id(PROFILE_ID).build();
        ProfileDTO expected = ProfileDTO.builder().id(PROFILE_ID).build();

        given(userService.getUserByEmail("m@mail.com")).willReturn(storifyUser);
        given(profileConverter.convertProfileDTOToProfile(PROFILE_ID, cart, profileDTO)).willReturn(profile);
        given(profileRepository.save(profile)).willReturn(profile);

        ProfileDTO actual = profileService.updateProfileByEmail("m@mail.com", profileDTO);

        assertEquals(expected, actual);

        then(profileRepository).should(only()).save(profile);
        then(profileConverter).should(only()).convertProfileDTOToProfile(PROFILE_ID, cart, profileDTO);
        then(userService).should(only()).getUserByEmail("m@mail.com");
    }
}
