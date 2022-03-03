package internship.mbicycle.storify.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import java.util.Optional;

import internship.mbicycle.storify.model.Purchase;
import internship.mbicycle.storify.repository.PurchaseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/sql/insert-purchase.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/delete-purchase.sql", executionPhase = AFTER_TEST_METHOD)
public class PurchaseRepositoryTest {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Test
    void shouldGetPurchaseByUniqueCode() {
        final String code = "uniqueCode";
        Optional<Purchase> purchase = purchaseRepository.findPurchaseByUniqueCode(code);
        assertEquals(code,purchase.get().getUniqueCode());
    }
}
