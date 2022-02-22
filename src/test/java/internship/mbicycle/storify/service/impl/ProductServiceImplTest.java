package internship.mbicycle.storify.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

import java.util.Optional;

import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;
    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        product = Product.builder()
            .productName("Car")
            .price(1000)
            .id(89L)
            .count(57)
            .build();

        productDTO = ProductDTO.builder()
            .productName("Car")
            .price(1000)
            .id(89L)
            .count(57)
            .build();
    }

    @Test
    void testFindByIncorrectId() {
        final Long id = 5L;
        given(productRepository.findById(id)).willReturn(Optional.empty());
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
            productService.getProductById(id));
        assertEquals("Product not found.", thrown.getMessage());
    }

    @Test
    void testGetProductById() {
        final Long id = 89L;
        given(productRepository.findById(id)).willReturn(Optional.of(product));
        ProductDTO actual = productService.getProductById(id);
        assertEquals(productDTO, actual);
    }

    @Test
    void testConvertProductToProductDTO() {
        ProductDTO test = ProductServiceImpl.convertToDTO(product);
        assertInstanceOf(ProductDTO.class, test);
    }

    @Test
    void testConvertProductDTOToProduct() {
        Product test = ProductServiceImpl.convertToProduct(productDTO);
        assertInstanceOf(Product.class, test);
    }

    @Test
    void testSaveProduct() {
        productService.saveProduct(productDTO);
        then(productRepository).should(only()).save(product);
    }
}
