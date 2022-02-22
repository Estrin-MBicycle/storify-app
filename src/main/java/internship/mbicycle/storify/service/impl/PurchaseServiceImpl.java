package internship.mbicycle.storify.service.impl;

import static internship.mbicycle.storify.util.ExceptionMessage.NOT_FOUND_PURCHASE;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import internship.mbicycle.storify.dto.PurchaseDTO;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.model.Purchase;
import internship.mbicycle.storify.repository.PurchaseRepository;
import internship.mbicycle.storify.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final GeneratorUniqueCodeImpl generatorUniqueCode;

    public static Purchase convertDTOToOrder(PurchaseDTO purchaseDTO) {

        if (purchaseDTO == null) {
            return null;
        }

        return Purchase.builder()
            .purchaseDate(purchaseDTO.getPurchaseDate())
            .id(purchaseDTO.getId())
            .profileId(purchaseDTO.getProfileId())
            .uniqueCode(purchaseDTO.getUniqueCode())
            .products(purchaseDTO.getProductDTOMap())
            .delivered(purchaseDTO.isDelivered())
            .build();
    }

    public static PurchaseDTO convertOrderToDTO(Purchase purchase) {

        return PurchaseDTO.builder()
            .id(purchase.getId())
            .purchaseDate(purchase.getPurchaseDate())
            .price(purchase.getPrice())
            .profileId(purchase.getProfileId())
            .uniqueCode(purchase.getUniqueCode())
            .productDTOMap(purchase.getProducts())
            .delivered(purchase.isDelivered())
            .build();
    }

    @Override
    public List<PurchaseDTO> getAllPurchasesByProfileIdAndDelivered(Long profileId, boolean isDelivered) {
        return purchaseRepository.findAllByProfileIdAndDelivered(profileId, isDelivered).stream()
            .map(PurchaseServiceImpl::convertOrderToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public PurchaseDTO getPurchaseById(Long id) {
        Purchase purchase = purchaseRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException(NOT_FOUND_PURCHASE));
        return convertOrderToDTO(purchase);
    }

    @Override
    public PurchaseDTO getPurchaseByUniqueCode(String uniqueCode) {
        Purchase purchase = purchaseRepository.findPurchaseByUniqueCode(uniqueCode).orElseThrow(() ->
            new ResourceNotFoundException(NOT_FOUND_PURCHASE));
        return convertOrderToDTO(purchase);
    }

    @Override
    public List<PurchaseDTO> getAllPurchasesByProfileId(Long profileId) {
        return purchaseRepository.findAllByProfileId(profileId).stream()
            .map(PurchaseServiceImpl::convertOrderToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public PurchaseDTO savePurchase(PurchaseDTO purchaseDTO) {
        purchaseDTO.setUniqueCode(generatorUniqueCode.generationUniqueCode());
        purchaseDTO.setPurchaseDate(LocalDate.now());
        purchaseRepository.save(convertDTOToOrder(purchaseDTO));
        return purchaseDTO;
    }

    @Override
    public List<PurchaseDTO> getAllPurchases() {
        return purchaseRepository.findAll().stream()
            .map(PurchaseServiceImpl::convertOrderToDTO)
            .collect(Collectors.toList());
    }
}
