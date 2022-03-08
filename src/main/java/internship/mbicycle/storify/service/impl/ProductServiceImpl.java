package internship.mbicycle.storify.service.impl;

import static internship.mbicycle.storify.util.ExceptionMessage.NOT_FOUND_PRODUCT;
import static internship.mbicycle.storify.util.ExceptionMessage.NOT_FOUND_USER;
import static java.lang.String.format;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import internship.mbicycle.storify.converter.ProductConverter;
import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.dto.PurchaseDTO;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.exception.StorifyUserNotFoundException;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.model.Store;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.repository.ProductRepository;
import internship.mbicycle.storify.repository.StoreRepository;
import internship.mbicycle.storify.repository.StorifyUserRepository;
import internship.mbicycle.storify.service.MailService;
import internship.mbicycle.storify.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final StoreRepository storeRepository;
    private final MailService mailService;
    private final StorifyUserRepository storifyUserRepository;

    @Override
    public List<ProductDTO> getAllProductsFromStore(Long storeId) {
        return productRepository.findAllByStoreId(storeId).stream()
            .map(productConverter::convertProductToProductDTO)
            .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductDTOById(Long id) {
        Product productDb = productRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException(format(NOT_FOUND_PRODUCT, id)));
        return productConverter.convertProductToProductDTO(productDb);
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
            new ResourceNotFoundException(format(NOT_FOUND_PRODUCT, id)));
        sendMessageIfProductOnSaleAgain(productDb, product);
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
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException(format(NOT_FOUND_PRODUCT, id)));
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

    private void sendMessageIfProductOnSaleAgain(Product product, ProductDTO productDTO) {
        if (product.getCount() == 0 && productDTO.getCount() > 0) {
            List<Profile> profiles = product.getProfiles();
            List<String> emails = profiles.stream().map(storifyUserRepository::findByProfile)
                .map(user -> user.orElseThrow(() -> new StorifyUserNotFoundException(NOT_FOUND_USER)))
                .map(StorifyUser::getEmail)
                .collect(Collectors.toList());
            mailService.sendFavoriteMessage(emails, productDTO);
        }
    }

    @Override
    public void changeProductCountAfterThePurchase(PurchaseDTO purchaseDTO) {
        Map<Long, Integer> productDTOMap = purchaseDTO.getProductDTOMap();
        for (Map.Entry<Long, Integer> entry : productDTOMap.entrySet()) {
            Product product = getProductById(entry.getKey());
            product.setCount(product.getCount() - entry.getValue());
            productRepository.save(product);
        }
    }
}
