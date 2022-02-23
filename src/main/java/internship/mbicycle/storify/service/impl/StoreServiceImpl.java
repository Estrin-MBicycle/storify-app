package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.converter.StoreConverter;
import internship.mbicycle.storify.dto.StoreDTO;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.model.Store;
import internship.mbicycle.storify.repository.ProfileRepository;
import internship.mbicycle.storify.repository.StoreRepository;
import internship.mbicycle.storify.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static internship.mbicycle.storify.util.ExceptionMessage.NOT_FOUND_STORE;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final ProfileRepository profileRepository;
    private final StoreConverter storeConverter;


    @Override
    public List<StoreDTO> findStoresByProfileId(Long profileId) {
        return storeRepository.findStoresByProfileId(profileId)
                .stream()
                .map(storeConverter::fromStoreToStoreDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StoreDTO> findStoresByProfileIdNot(Long profileId) {
        return storeRepository.findStoresByProfileIdNot(profileId)
                .stream()
                .map(storeConverter::fromStoreToStoreDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StoreDTO findStoreByIdAndProfileId(Long id, Long profileId) {
        Store store = storeRepository.findStoreByIdAndProfileId(id, profileId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(NOT_FOUND_STORE, id)));
        return storeConverter.fromStoreToStoreDTO(store);
    }

    @Override
    public StoreDTO findStoreById(Long id) {
        Store store = storeRepository.findStoreById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(NOT_FOUND_STORE, id)));
        return storeConverter.fromStoreToStoreDTO(store);
    }

    @Override
    public StoreDTO saveStore(StoreDTO storeDTO, Long profileId) {
        Store store = storeConverter.fromStoreDTOToStore(storeDTO);
        Profile profile = profileRepository.getById(profileId);
        store.setProfile(profile);
        Store save = storeRepository.save(store);
        return storeConverter.fromStoreToStoreDTO(save);
    }

    @Override
    public StoreDTO updateStore(StoreDTO storeDTO, Long id, Long profileId) {
        Store store = storeRepository.findStoreByIdAndProfileId(id,profileId).orElseThrow(() -> new ResourceNotFoundException(String.format(NOT_FOUND_STORE, id)));
        store.setStoreName(storeDTO.getStoreName());
        store.setDescription(storeDTO.getDescription());
        store.setAddress(storeDTO.getAddress());
        Profile profile = profileRepository.getById(profileId);
        store.setProfile(profile);
        Store save = storeRepository.save(store);
        return storeConverter.fromStoreToStoreDTO(save);
    }

    @Override
    public void deleteByIdAndProfileId(Long id, Long profileId) {
        storeRepository.deleteByIdAndProfileId(id, profileId);
    }

    @Override
    public List<StoreRepository.PurchasedAndNotPaidProduct> findMostPurchasedProductsInStore(Long id, Long limit) {
        return storeRepository.findMostPurchasedProductsInStore(id, limit);
    }

    @Override
    public List<StoreRepository.PurchasedAndNotPaidProduct> findLestPurchasedProductsInStore(Long id, Long limit) {
        return storeRepository.findLestPurchasedProductsInStore(id, limit);
    }

    @Override
    public void deleteAllByProfileId(Long profileId) {
        storeRepository.deleteAllByProfileId(profileId);
    }
}
