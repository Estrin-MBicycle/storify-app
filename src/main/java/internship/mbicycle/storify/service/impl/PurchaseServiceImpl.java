package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.converter.PurchaseConverter;
import internship.mbicycle.storify.dto.PurchaseDTO;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.model.Purchase;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.repository.PurchaseRepository;
import internship.mbicycle.storify.service.MailService;
import internship.mbicycle.storify.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static internship.mbicycle.storify.util.ExceptionMessage.NOT_FOUND_PURCHASE;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final GeneratorUniqueCodeImpl generatorUniqueCode;
    private final PurchaseConverter purchaseConverter;
    private final MailService mailService;

    @Override
    public List<PurchaseDTO> getAllPurchasesByProfileIdAndDelivered(Long profileId, boolean isDelivered) {
        return purchaseRepository.findAllByProfileIdAndDelivered(profileId, isDelivered).stream()
                .map(purchaseConverter::convertPurchaseToPurchaseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PurchaseDTO getPurchaseById(Long id) {
        Purchase purchase = purchaseRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(NOT_FOUND_PURCHASE));
        return purchaseConverter.convertPurchaseToPurchaseDTO(purchase);
    }

    @Override
    public PurchaseDTO getPurchaseByUniqueCode(String uniqueCode) {
        Purchase purchase = purchaseRepository.findPurchaseByUniqueCode(uniqueCode).orElseThrow(() ->
                new ResourceNotFoundException(NOT_FOUND_PURCHASE));
        return purchaseConverter.convertPurchaseToPurchaseDTO(purchase);
    }

    @Override
    public List<PurchaseDTO> getAllPurchasesByProfileId(Long profileId) {
        return purchaseRepository.findAllByProfileId(profileId).stream()
                .map(purchaseConverter::convertPurchaseToPurchaseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PurchaseDTO savePurchase(StorifyUser user, PurchaseDTO purchaseDTO) {
        purchaseDTO.setUniqueCode(generatorUniqueCode.generationUniqueCode());
        purchaseDTO.setPurchaseDate(LocalDate.now());
        purchaseRepository.save(purchaseConverter.convertPurchaseDTOToPurchase(purchaseDTO));
        mailService.sendPurchaseMessage(user, purchaseDTO);
        return purchaseDTO;
    }

    @Override
    public List<PurchaseDTO> getAllPurchases() {
        return purchaseRepository.findAll().stream()
                .map(purchaseConverter::convertPurchaseToPurchaseDTO)
                .collect(Collectors.toList());
    }
}
