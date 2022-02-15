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
        return convertToDto(productDb);
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
        return convertToDto(productDb);
    }

    public Product convertToEntity(ProductDTO dto) {
        if (dto == null) {
            return null;
        }
        return Product.builder()
                .count(dto.getCount())
                .description(dto.getDescription())
                .id(dto.getId())
                .image(dto.getImage())
                .productName(dto.getProductName())
                .price(dto.getPrice())
                .storeId(dto.getStoreId())
                .build();
    }

    private ProductDTO convertToDto(Product entity) {
        return ProductDTO.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .count(entity.getCount())
                .image(entity.getImage())
                .price(entity.getPrice())
                .productName(entity.getProductName())
                .storeId(entity.getStoreId())
                .build();
    }
}
