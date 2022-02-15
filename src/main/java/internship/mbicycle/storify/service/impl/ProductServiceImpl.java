package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.exception.ErrorCode;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.repository.ProductRepository;
import internship.mbicycle.storify.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductDTO findById(Long id) {
       Product productDb =  productRepository.findById(id).orElseThrow(() ->
               new ResourceNotFoundException(ErrorCode.NOT_FOUND_PRODUCT));
        return convertToDTO(productDb);
    }

    @Override
    public void removeProduct(Long id) {
        productRepository.removeById(id);
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDto) {
        Product product = convertToEntity(productDto);
        productRepository.save(product);
        return productDto;
    }

    @Override
    public void removeAllProductByStoreId(Long storeId) {
        productRepository.removeAllByStoreId(storeId);
    }

    @Override
    public void removeProductByStoreIdAndId(Long storeId, Long productId) {
        productRepository.removeByStoreIdAndId(storeId, productId);
    }

    @Override
    public ProductDTO findByName(String name) {
        Product productDb =  productRepository.findProductByProductName(name).orElseThrow(() ->
        new ResourceNotFoundException(ErrorCode.NOT_FOUND_PRODUCT));
        return convertToDTO(productDb);
    }

    public Product convertToEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }
        return Product.builder()
                .count(productDTO.getCount())
                .description(productDTO.getDescription())
                .id(productDTO.getId())
                .image(productDTO.getImage())
                .productName(productDTO.getProductName())
                .price(productDTO.getPrice())
                .storeId(productDTO.getStoreId())
                .build();
    }

    private ProductDTO convertToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .description(product.getDescription())
                .count(product.getCount())
                .image(product.getImage())
                .price(product.getPrice())
                .productName(product.getProductName())
                .storeId(product.getStoreId())
                .build();
    }
}
