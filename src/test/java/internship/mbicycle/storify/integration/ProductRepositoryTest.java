package internship.mbicycle.storify.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import java.util.Optional;

import internship.mbicycle.storify.TestMariaDbContainer;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/sql/insert-product.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/delete-product.sql", executionPhase = AFTER_TEST_METHOD)
@TestMariaDbContainer
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldGetProductByName() {
        final String name = "TestProduct";
        Optional<Product> productByName = productRepository.findProductByProductName(name);
        assertEquals(name, productByName.get().getProductName());
    }
}