package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.dto.IncomePeriodDTO;
import internship.mbicycle.storify.dto.StoreDTO;
import internship.mbicycle.storify.repository.StoreRepository;
import internship.mbicycle.storify.service.StoreService;
import internship.mbicycle.storify.util.IncomePeriod;
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
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/my")
    public List<StoreDTO> getStoresByProfile(@ApiIgnore Principal principal) {
        return storeService.getStoresByEmail(principal.getName());
    }

    @GetMapping
    public List<StoreDTO> getStores() {
        return storeService.getStores();
    }

    @GetMapping("/{id}")
    public StoreDTO getStore(@PathVariable Long id) {
        return storeService.getStoreById(id);
    }

    @PostMapping
    public StoreDTO saveStore(@Valid @RequestBody StoreDTO storeDTO, @ApiIgnore Principal principal)  {
        return storeService.saveStore(storeDTO, principal.getName());
    }

    @PutMapping("/{id}")
    public StoreDTO updateStore(@Valid @RequestBody StoreDTO storeDTO, @PathVariable Long id, @ApiIgnore Principal principal) {
        return storeService.updateStore(storeDTO, id, principal.getName());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStore(@PathVariable Long id, @ApiIgnore Principal principal) {
        storeService.deleteByIdAndEmail(id, principal.getName());
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

    @GetMapping("/income/{profileId}")
    public IncomePeriodDTO getIncome(@PathVariable long profileId) {
        return storeService.getIncome(profileId);
    }

    @GetMapping("/income/{incomePeriod}/{profileId}")
    public Integer getIncomeForPeriod(@PathVariable IncomePeriod incomePeriod, @PathVariable long profileId) {
        return storeService.getIncomeForPeriod(incomePeriod, profileId);
    }

}
