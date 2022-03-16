package internship.mbicycle.storify.integration;

import internship.mbicycle.storify.TestMariaDbContainer;
import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.model.Store;
import internship.mbicycle.storify.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/sql/insert_data.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/delete_data.sql", executionPhase = AFTER_TEST_METHOD)
@TestMariaDbContainer
public class StoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

    @Test
    void shouldFindStoresByProfileId() {
        final Profile profile = Profile.builder()
                .id(1L)
                .name("name")
                .surname("surname")
                .town("town")
                .address("address")
                .phone("phone").build();
        final Store store = Store.builder()
                .id(1L)
                .storeName("store_name")
                .description("description")
                .address("address")
                .profile(profile).build();
        final List<Store> expected = new ArrayList<>();
        expected.add(store);
        final List<Store> actual = storeRepository.findStoresByProfileId(1L);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindStoreByIdAndProfileId() {
        final Profile profile = Profile.builder()
                .id(1L)
                .name("name")
                .surname("surname")
                .town("town")
                .address("address")
                .phone("phone").build();
        final Store expected = Store.builder()
                .id(1L)
                .storeName("store_name")
                .description("description")
                .address("address")
                .profile(profile).build();
        final Optional<Store> actual = storeRepository.findStoreByIdAndProfileId(1L, 1L);
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldFindStoreById() {
        final Optional<Store> storeById = storeRepository.findById(1L);
        assertTrue(storeById.isPresent());
    }

    @Test
    void shouldSaveStore() {
        final Profile profile = Profile.builder()
                .id(1L)
                .name("name")
                .surname("surname")
                .town("town")
                .address("address")
                .phone("phone").build();
        final Store store = Store.builder()
                .id(1L)
                .storeName("store_name1")
                .description("description1")
                .address("address1")
                .profile(profile).build();
        final Store expected = Store.builder()
                .id(1L)
                .storeName("store_name1")
                .description("description1")
                .address("address1")
                .profile(profile).build();
        final Store actual = storeRepository.save(store);
        assertEquals(expected, actual);
    }

    @Test
    void shouldGetIncomeForAllTime() {
        final int actual = storeRepository.getIncomeForAllTime(1L);
        assertEquals(110, actual);
    }

    @Test
    void shouldGetIncomeForMonths() {
        final int actual = storeRepository.getIncomeForMonths(1L, 1);
        assertEquals(55, actual);
    }

    @Test
    void shouldGetIncomeForDay() {
        final int actual = storeRepository.getIncomeForDay(1L);
        assertEquals(0, actual);
    }
}
