package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.dto.StoreDTO;
import internship.mbicycle.storify.repository.StoreRepository;
import internship.mbicycle.storify.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/{profileId}")
    public List<StoreDTO> getAllProfileStores(@PathVariable Long profileId) {
        return storeService.findStoresByProfileId(profileId);
    }

    @GetMapping("/all/{profileId}")
    public List<StoreDTO> getAllStores(@PathVariable Long profileId) {
        return storeService.findStoresByProfileIdNot(profileId);
    }

    @GetMapping("/{id}/{profileId}")
    public StoreDTO findProfileStore(@PathVariable Long id, @PathVariable Long profileId) {
        return storeService.findStoreByIdAndProfileId(id, profileId);
    }

    @GetMapping("/store/{id}")
    public StoreDTO findStore(@PathVariable Long id) {
        return storeService.findStoreById(id);
    }

    @PostMapping("/{profileId}")
    public StoreDTO saveStore(@RequestBody StoreDTO storeDTO, @PathVariable Long profileId)  {
        return storeService.saveStore(storeDTO, profileId);
    }

    @PutMapping("/{id}/{profileId}")
    public StoreDTO updateStore(@RequestBody StoreDTO storeDTO, @PathVariable Long id, @PathVariable Long profileId) {
        return storeService.updateStore(storeDTO, id, profileId);
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<?> deleteAllStoresByProfileId(@PathVariable Long profileId) {
        storeService.deleteAllByProfileId(profileId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/{profileId}")
    public ResponseEntity<?> deleteStoreByProfileId(@PathVariable Long id, @PathVariable Long profileId) {
        storeService.deleteByIdAndProfileId(id, profileId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/products/most/{id}/{limit}")
    public List<StoreRepository.PurchasedAndNotPaidProduct> findMostPurchasedProductsInStore(@PathVariable Long id, @PathVariable Long limit) {
        return storeService.findMostPurchasedProductsInStore(id, limit);
    }

    @GetMapping("/products/lest/{id}/{limit}")
    public List<StoreRepository.PurchasedAndNotPaidProduct> findLestPurchasedProductsInStore(@PathVariable Long id, @PathVariable Long limit) {
        return storeService.findLestPurchasedProductsInStore(id, limit);
    }
}
