package internship.mbicycle.storify.integration;

import internship.mbicycle.storify.TestMariaDbContainer;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.repository.StorifyUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/sql/insert_storify_user.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/delete_storify_user.sql", executionPhase = AFTER_TEST_METHOD)
@TestMariaDbContainer
class StorifyUserRepositoryTest {

    @Autowired
    private StorifyUserRepository userRepository;

    @Test
    void shouldFindByEmail() {
        final String email = "test@mail.ru";
        Optional<StorifyUser> byEmail = userRepository.findByEmail(email);
        assertTrue(byEmail.isPresent());
    }

    @Test
    void shouldFindByActivationCode() {
        final String activationCode = "123456";
        Optional<StorifyUser> byActivationCode = userRepository.findByActivationCode(activationCode);
        assertTrue(byActivationCode.isPresent());
    }
}