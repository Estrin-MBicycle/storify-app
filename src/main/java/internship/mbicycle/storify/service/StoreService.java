package internship.mbicycle.storify.service;

import internship.mbicycle.storify.dto.IncomePeriodDTO;
import internship.mbicycle.storify.dto.StoreDTO;
import internship.mbicycle.storify.repository.StoreRepository;
import internship.mbicycle.storify.util.IncomePeriod;

import java.util.List;

public interface StoreService {

    List<StoreDTO> findStoresByEmail(String email);

    List<StoreDTO> findStoresByEmailNot(String email);

    StoreDTO findStoreByIdAndEmail(Long id, String email);

    StoreDTO findStoreById(Long id);

    StoreDTO saveStore(StoreDTO storeDTO, String email);

    StoreDTO updateStore(StoreDTO storeDTO, Long id, String email);

    void deleteAllByEmail(String email);

    void deleteByIdAndEmail(Long id, String email);

    List<StoreRepository.PurchasedAndNotPaidProduct> findMostPurchasedProductsInStore(Long id, Long limit);

    List<StoreRepository.PurchasedAndNotPaidProduct> findLestPurchasedProductsInStore(Long id, Long limit);

    IncomePeriodDTO getIncome(long profileId);

    Integer getIncomeForPeriod(IncomePeriod incomePeriod, long profileId);

}
