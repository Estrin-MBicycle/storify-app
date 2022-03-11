package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.converter.StoreConverter;
import internship.mbicycle.storify.dto.IncomePeriodDTO;
import internship.mbicycle.storify.dto.StoreDTO;
import internship.mbicycle.storify.exception.ResourceNotFoundException;
import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.model.Store;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.repository.ProfileRepository;
import internship.mbicycle.storify.repository.StoreRepository;
import internship.mbicycle.storify.service.StoreService;
import internship.mbicycle.storify.service.StorifyUserService;
import internship.mbicycle.storify.util.IncomePeriod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static internship.mbicycle.storify.util.ExceptionMessage.NOT_FOUND_STORE;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final ProfileRepository profileRepository;
    private final StoreConverter storeConverter;
    private final StorifyUserService userService;

    @Override
    public List<StoreDTO> findStoresByEmail(String email) {
        StorifyUser user = userService.getUserByEmail(email);
        return storeRepository.findStoresByProfileId(user.getProfile().getId())
                .stream()
                .map(storeConverter::fromStoreToStoreDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StoreDTO> findStoresByEmailNot(String email) {
        StorifyUser user = userService.getUserByEmail(email);
        return storeRepository.findStoresByProfileIdNot(user.getProfile().getId())
                .stream()
                .map(storeConverter::fromStoreToStoreDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StoreDTO findStoreByIdAndEmail(Long id, String email) {
        StorifyUser user = userService.getUserByEmail(email);
        Store store = storeRepository.findStoreByIdAndProfileId(id, user.getProfile().getId())
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
    public StoreDTO saveStore(StoreDTO storeDTO, String email) {
        StorifyUser user = userService.getUserByEmail(email);
        Store store = storeConverter.fromStoreDTOToStore(storeDTO);
        Profile profile = profileRepository.getById(user.getProfile().getId());
        store.setProfile(profile);
        Store save = storeRepository.save(store);
        return storeConverter.fromStoreToStoreDTO(save);
    }

    @Override
    public StoreDTO updateStore(StoreDTO storeDTO, Long id, String email) {
        StorifyUser user = userService.getUserByEmail(email);
        Store store = storeRepository.findStoreByIdAndProfileId(id, user.getProfile().getId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format(NOT_FOUND_STORE, id)));
        store.setStoreName(storeDTO.getStoreName());
        store.setDescription(storeDTO.getDescription());
        store.setAddress(storeDTO.getAddress());
        Profile profile = profileRepository.getById(user.getProfile().getId());
        store.setProfile(profile);
        Store save = storeRepository.save(store);
        return storeConverter.fromStoreToStoreDTO(save);
    }

    @Override
    public void deleteByIdAndEmail(Long id, String email) {
        StorifyUser user = userService.getUserByEmail(email);
        storeRepository.deleteByIdAndProfileId(id, user.getProfile().getId());
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
    public void deleteAllByEmail(String email) {
        StorifyUser user = userService.getUserByEmail(email);
        storeRepository.deleteAllByProfileId(user.getProfile().getId());
    }

    @Override
    public IncomePeriodDTO getIncome(long profileId) {
        Map<IncomePeriod, Integer> map = Map.of(
                IncomePeriod.ALL_TIME, storeRepository.getIncomeForAllTime(profileId),
                IncomePeriod.YEAR, storeRepository.getIncomeForMonths(profileId, 12),
                IncomePeriod.HALF_YEAR, storeRepository.getIncomeForMonths(profileId, 6),
                IncomePeriod.LAST_MONTH, storeRepository.getIncomeForMonths(profileId, 1),
                IncomePeriod.TODAY, storeRepository.getIncomeForDay(profileId)
        );
        return IncomePeriodDTO.builder().income(map).build();
    }

    @Override
    public Integer getIncomeForPeriod(IncomePeriod incomePeriod, long profileId) {
        return Map.of(
                IncomePeriod.ALL_TIME, storeRepository.getIncomeForAllTime(profileId),
                IncomePeriod.YEAR, storeRepository.getIncomeForMonths(profileId, 12),
                IncomePeriod.HALF_YEAR, storeRepository.getIncomeForMonths(profileId, 6),
                IncomePeriod.LAST_MONTH, storeRepository.getIncomeForMonths(profileId, 1),
                IncomePeriod.TODAY, storeRepository.getIncomeForDay(profileId)
        ).get(incomePeriod);
    }

}
