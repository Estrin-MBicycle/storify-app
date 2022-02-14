package internship.mbicycle.storify.service;

import internship.mbicycle.storify.model.Store;
import internship.mbicycle.storify.repository.StoreRepository;
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
    public Optional<Store> findStoresByProfileIdAndId(Long profileId, Long id) {
        return Optional.ofNullable(storeRepository.findStoresByProfileIdAndId(profileId, id))
                .orElseThrow(() -> new RuntimeException()); //В дальшейшем напишу своё исключение
    }
}
