package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.repository.ProfileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProfileServiceImplTest {

    private ProfileServiceImpl profileService;

    @BeforeEach
    void setUp() {
        profileService = new ProfileServiceImpl(Mockito.mock(ProfileRepository.class));
    }

    @Test
    void testFindByIncorrectId() {
        assertThrows(ResourceNotFoundException.class, () -> profileService.getById(-7L));
    }

    @Test
    void testMessageFindByIncorrectId() {
        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () ->
                profileService.getById(-7L));
        Assertions.assertEquals("Profile not found.", thrown.getMessage());
    }
}
