package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;


class ProductServiceImplTest {

    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(Mockito.mock(ProductRepository.class));
    }

    @Test
    void testFindByIncorrectId() {
        assertThrows(ResourceNotFoundException.class, () -> productService.findById(-4L));
    }

    @Test
    void testMessageFindByIncorrectId() {
        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () ->
                productService.findById(-45L));
        Assertions.assertEquals("Product not found.", thrown.getMessage());
    }
}
