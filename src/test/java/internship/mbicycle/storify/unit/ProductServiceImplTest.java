package internship.mbicycle.storify.unit;

import static internship.mbicycle.storify.util.ExceptionMessage.NOT_FOUND_PRODUCT;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

import java.security.Principal;
import java.util.Optional;

import internship.mbicycle.storify.converter.ProductConverter;
import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.model.Store;
import internship.mbicycle.storify.repository.ProductRepository;
import internship.mbicycle.storify.repository.StoreRepository;
import internship.mbicycle.storify.service.PermissionsCheckService;
import internship.mbicycle.storify.service.impl.ProductServiceImpl;
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
    @Mock
    private ProductConverter productConverter;
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private PermissionsCheckService permissionsCheckService;

    @InjectMocks
    private ProductServiceImpl productService;
    private Product product;
    private ProductDTO productDTO;
    private Store store;
    private Principal principal;


    @BeforeEach
    void setUp() {
        store = Store.builder()
            .id(9L)
            .address("Test")
            .description("Store Test")
            .build();

        principal = () -> "vasili@gmail.com";

        product = Product.builder()
            .productName("Car")
            .price(1000)
            .id(89L)
            .count(57)
            .store(store)
            .build();

        productDTO = ProductDTO.builder()
            .productName("Car")
            .price(1000)
            .id(89L)
            .count(57)
            .storeId(9L)
            .build();

    }

    @Test
    void testFindByIncorrectId() {
        final Long id = 5L;
        given(productRepository.findById(id)).willReturn(Optional.empty());
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
            productService.getProductDTOById(id));
        assertEquals(format(NOT_FOUND_PRODUCT, id), thrown.getMessage());
    }

    @Test
    void testGetProductById() {
        final Long id = 89L;
        given(productConverter.convertProductToProductDTO(product)).willReturn(productDTO);
        given(productRepository.findById(id)).willReturn(Optional.of(product));
        ProductDTO actual = productService.getProductDTOById(id);
        assertEquals(productDTO, actual);
    }

    @Test
    void testSaveProduct() {
        final Long storeId = 9L;
        given(productConverter.convertProductDTOToProduct(productDTO)).willReturn(product);
        given(productConverter.convertProductToProductDTO(product)).willReturn(productDTO);
        given(permissionsCheckService.checkPermissionByStoreId(principal, storeId)).willReturn(storeId);
        given(storeRepository.getById(storeId)).willReturn(store);
        given(productRepository.save(product)).willReturn(product);
        assertEquals(productDTO, productService.saveProduct(productDTO, principal));
        then(productRepository).should(only()).save(product);
    }
}
