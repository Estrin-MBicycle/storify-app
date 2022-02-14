package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.model.Store;
import internship.mbicycle.storify.repository.StoreRepository;
import internship.mbicycle.storify.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    @Override
    public List<Store> findStoresByProfileId(Long id) {
        return storeRepository.findStoresByProfileId(id);
    }

    @Override
    public Optional<Store> findStoresByIdAndProfileId(Long id, Long profileId) {
        return Optional.ofNullable(storeRepository.findStoresByIdAndProfileId(id, profileId))
                .orElseThrow(() -> new RuntimeException()); //В дальшейшем напишу своё исключение
    }

    @Override
    public void updateStoreInfo(String storeName, String description, String address, Long id) {
        storeRepository.updateStoreInfo(storeName, description, address, id);
    }
}
