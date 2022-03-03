package internship.mbicycle.storify.integration;

import internship.mbicycle.storify.TestMariaDbContainer;
import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/sql/insert_profile.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/delete_profile.sql", executionPhase = AFTER_TEST_METHOD)
@TestMariaDbContainer
public class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository profileRepository;

    @Test
    void shouldFindProfileById() {
        final long id = 1;
        Optional<Profile> profileById = profileRepository.findById(id);
        assertTrue(profileById.isPresent());
    }

    @Test
    void shouldGetProfileById() {
        Profile expected = Profile.builder()
                .id(1L)
                .name("name")
                .surname("surname")
                .town("town")
                .address("address")
                .phone("phone").build();
        final long id = 1;
        Profile profileById = profileRepository.getById(id);
        assertEquals(expected, profileById);
    }
}
