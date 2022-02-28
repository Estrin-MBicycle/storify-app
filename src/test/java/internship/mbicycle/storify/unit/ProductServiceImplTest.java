package internship.mbicycle.storify.unit;

import internship.mbicycle.storify.converter.ProductConverter;
import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.model.Store;
import internship.mbicycle.storify.repository.ProductRepository;
import internship.mbicycle.storify.repository.StoreRepository;
import internship.mbicycle.storify.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductConverter productConverter;
    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private ProductServiceImpl productService;
    private Product product;
    private ProductDTO productDTO;
    private Store store;

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

        store = Store.builder()
                .id(9L)
                .address("Test")
                .description("Store Test")
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
        given(productConverter.convertProductToProductDTO(product)).willReturn(productDTO);
        given(productRepository.findById(id)).willReturn(Optional.of(product));
        ProductDTO actual = productService.getProductById(id);
        assertEquals(productDTO, actual);
    }

    @Test
    void testSaveProduct() {
        final Long storeId = 9L;
        given(productConverter.convertProductDTOToProduct(productDTO)).willReturn(product);
        given(productConverter.convertProductToProductDTO(product)).willReturn(productDTO);
        given(storeRepository.getById(storeId)).willReturn(store);
        given(productRepository.save(product)).willReturn(product);
        assertEquals(productDTO, productService.saveProduct(productDTO, storeId));
        then(productRepository).should(only()).save(product);
    }
}
