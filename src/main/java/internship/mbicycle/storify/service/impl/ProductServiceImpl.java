package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.converter.ProductConverter;
import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.model.Store;
import internship.mbicycle.storify.integration.repository.ProductRepository;
import internship.mbicycle.storify.integration.repository.StoreRepository;
import internship.mbicycle.storify.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static internship.mbicycle.storify.util.ExceptionMessage.NOT_FOUND_PRODUCT;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final StoreRepository storeRepository;

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
    public ProductDTO updateProduct(ProductDTO product, Long id, Long storeId) {
        Product productDb = productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(NOT_FOUND_PRODUCT));
        productDb.setProductName(product.getProductName());
        productDb.setDescription(product.getDescription());
        productDb.setCount(product.getCount());
        productDb.setPrice(product.getPrice());
        productDb.setImage(product.getImage());
        Store store = storeRepository.getById(storeId);
        productDb.setStore(store);
        Product save = productRepository.save(productDb);
        return productConverter.convertProductToProductDTO(save);
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDto, Long storeId) {
        Product product = productConverter.convertProductDTOToProduct(productDto);
        Store store = storeRepository.getById(storeId);
        product.setStore(store);
        Product save = productRepository.save(product);
        return productConverter.convertProductToProductDTO(save);
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
}
