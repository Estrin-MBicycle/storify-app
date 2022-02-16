package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;


class ProductServiceImplTest {

    private ProductServiceImpl productService;
    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(Mockito.mock(ProductRepository.class));

        product = Product.builder()
                .productName("Car")
                .price(1000)
                .id(89L)
                .count(57)
                .build();

        productDTO = ProductDTO.builder()
                .productName("Mobile")
                .price(200)
                .id(10L)
                .count(5)
                .build();
    }

    @Test
    void testFindByIncorrectId() {
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(-4L));
    }

    @Test
    void testMessageFindByIncorrectId() {
        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () ->
                productService.getProductById(-45L));
        Assertions.assertEquals("Product not found.", thrown.getMessage());
    }

    @Test
    void testConvertProductToProductDTO() {
        ProductDTO test = ProductServiceImpl.convertToDTO(product);
        Assertions.assertInstanceOf(ProductDTO.class, test);
    }

    @Test
    void testConvertProductDTOToProduct() {
        Product test = ProductServiceImpl.convertToProduct(productDTO);
        Assertions.assertInstanceOf(Product.class, test);
    }
}
