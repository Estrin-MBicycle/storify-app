package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.dto.BasketDTO;
import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.exception.ErrorCode;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.model.Basket;
import internship.mbicycle.storify.model.Product;
import internship.mbicycle.storify.repository.BasketRepository;
import internship.mbicycle.storify.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;


    @Override
    public BasketDTO getBasket(Long profileId) {
        Basket basketDb = basketRepository.findByProfileId(profileId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorCode.NOT_FOUND_BASKET));

        return convertBasketToDTO(basketDb);
    }


    @Override
    public void saveProduct(ProductDTO productDTO, Long basketId) {
        Basket basket = basketRepository.getById(basketId);
        List<Product> productList = basket.getProductList();
        productList.add(ProductServiceImpl.convertToProduct(productDTO));
        basket.setProductList(productList);
    }

    @Override
    public void removeProductFromBasket(ProductDTO productDTO, Long basketId) {
        Basket basket = basketRepository.getById(basketId);
        List<Product> productList = basket.getProductList();
        productList.remove(ProductServiceImpl.convertToProduct(productDTO));
        basket.setProductList(productList);
    }

    @Override
    public void removeAllProductsFromBasket(Product product, Long basketId) {
        Basket basket = basketRepository.getById(basketId);
        List<Product> productList = basket.getProductList();
        productList.clear();
        basket.setProductList(productList);

    }

    @Override
    public List<BasketDTO> getListOfOrders() {
        return basketRepository.findAll().stream()
                .map(BasketServiceImpl::convertBasketToDTO)
                .collect(Collectors.toList());
    }

    public static Basket convertDTOToBasket(BasketDTO basketDTO) {
        if (basketDTO == null) {
            return null;
        }
        List<Product> productList = basketDTO.getProductList()
                .stream()
                .map(ProductServiceImpl::convertToProduct)
                .collect(Collectors.toList());

        return Basket.builder()
                .id(basketDTO.getId())
                .profile(ProfileServiceImpl.convertProfileDTOToProfile(basketDTO.getProfile()))
                .productList(productList)
                .build();
    }

    public static BasketDTO convertBasketToDTO(Basket basket) {
        List<ProductDTO> productDTOList = basket.getProductList().stream()
                .map(ProductServiceImpl::convertToDTO)
                .collect(Collectors.toList());

        return BasketDTO.builder()
                .id(basket.getId())
                .profile(ProfileServiceImpl.convertProfileToProfileDTO(basket.getProfile()))
                .productList(productDTOList)
                .build();
    }
}
