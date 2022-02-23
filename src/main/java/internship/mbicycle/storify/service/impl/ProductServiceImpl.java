package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.converter.ProductConverter;
import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.exception.ErrorCode;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.exception.ValidationException;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.repository.ProductRepository;
import internship.mbicycle.storify.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static internship.mbicycle.storify.util.ExceptionMessage.NOT_FOUND_PRODUCT;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    @Override
    public List<ProductDTO> getAllProductsFromStore(Long storeId) {
        return productRepository.findAllByStoreId(storeId).stream()
                .map(productConverter::convertProductToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product productDb = productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(NOT_FOUND_PRODUCT));
        return productConverter.convertProductToProductDTO(productDb);
    }

    @Override
    public void removeProductById(Long id) {
        productRepository.removeProductById(id);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productConverter::convertProductToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDto) {
        validationProductDTO(productDto);
        Product product = productConverter.convertProductDTOToProduct(productDto);
        productRepository.save(product);
        return productDto;
    }

    @Override
    public void removeAllProductsByStoreId(Long storeId) {
        productRepository.removeAllByStoreId(storeId);
    }

    @Override
    public void removeProductByStoreIdAndId(Long storeId, Long productId) {
        productRepository.removeProductByStoreIdAndId(storeId, productId);
    }

    @Override
    public ProductDTO getProductByName(String name) {
        Product productDb = productRepository.findProductByProductName(name).orElseThrow(() ->
                new ResourceNotFoundException(NOT_FOUND_PRODUCT));
        return productConverter.convertProductToProductDTO(productDb);
    }

    private void validationProductDTO(ProductDTO productDTO) {
        Set<ConstraintViolation<ProductDTO>> violations = Validation.buildDefaultValidatorFactory()
                .getValidator()
                .validate(productDTO);
        if (!violations.isEmpty()) {
            throw new ValidationException(
                    ErrorCode.VALIDATION_ERROR,
                    String.join(";\n", violations.stream()
                            .map(it -> String.format("%s : rejected value [%s] - %s",
                                    it.getPropertyPath(), it.getInvalidValue(), it.getMessage()))
                            .toArray(String[]::new)));
        }
    }
}
