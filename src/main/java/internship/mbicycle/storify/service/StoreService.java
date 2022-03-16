package internship.mbicycle.storify.service;

import java.util.List;

import internship.mbicycle.storify.dto.IncomePeriodDTO;
import internship.mbicycle.storify.dto.StoreDTO;
import internship.mbicycle.storify.model.Store;
import internship.mbicycle.storify.repository.StoreRepository;
import internship.mbicycle.storify.util.IncomePeriod;

public interface StoreService {

    List<StoreDTO> getStoresByUserEmail(String email);

    List<StoreDTO> getStores();

    StoreDTO getStoreById(Long id);

    StoreDTO saveStore(StoreDTO storeDTO, String email);

    StoreDTO updateStore(StoreDTO storeDTO, Long id, String email);

    void deleteByIdAndUserEmail(Long id, String email);

    List<StoreRepository.PurchasedAndNotPaidProduct> findMostPurchasedProductsInStore(Long id, Long limit);

    List<StoreRepository.PurchasedAndNotPaidProduct> findLestPurchasedProductsInStore(Long id, Long limit);

    IncomePeriodDTO getIncome(long profileId);

    Integer getIncomeForPeriod(IncomePeriod incomePeriod, long profileId);

    Store getStoreById(Long storeId);

}
