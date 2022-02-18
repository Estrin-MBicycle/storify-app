package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.converter.StoreConverter;
import internship.mbicycle.storify.dto.StoreDTO;
import internship.mbicycle.storify.exception.ErrorCode;
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
    public StoreDTO findStoresByIdAndProfileId(Long id, Long profileId) {
        Store store = storeRepository.findStoresByIdAndProfileId(id, profileId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.NOT_FOUND_STORE));
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
        Store store = storeRepository.findStoresByIdAndProfileId(id,profileId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.NOT_FOUND_STORE));
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
    public void deleteAllByProfileId(Long profileId) {
        storeRepository.deleteAllByProfileId(profileId);
    }
}
