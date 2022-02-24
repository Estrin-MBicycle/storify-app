package internship.mbicycle.storify.service;

import internship.mbicycle.storify.dto.PeriodOfIncomeDTO;
import internship.mbicycle.storify.dto.StoreDTO;
import internship.mbicycle.storify.repository.StoreRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreService {
    List<StoreDTO> findStoresByProfileId(Long profileId);

    List<StoreDTO> findStoresByProfileIdNot(Long profileId);

    StoreDTO findStoreByIdAndProfileId(Long id, Long profileId);

    StoreDTO findStoreById(Long id);

    StoreDTO saveStore(StoreDTO storeDTO, Long profileId);

    StoreDTO updateStore(StoreDTO storeDTO, Long id, Long profileId);

    void deleteAllByProfileId(Long profileId);

    void deleteByIdAndProfileId(Long id, Long profileId);

    List<StoreRepository.PurchasedAndNotPaidProduct> findMostPurchasedProductsInStore(Long id, Long limit);

    List<StoreRepository.PurchasedAndNotPaidProduct> findLestPurchasedProductsInStore(Long id, Long limit);

    PeriodOfIncomeDTO getIncome(long profileId);

}
