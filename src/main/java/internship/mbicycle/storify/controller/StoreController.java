package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.dto.StoreDTO;
import internship.mbicycle.storify.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public List<StoreDTO> getAllStores(@PathVariable Long profileId) {
        return storeService.findStoresByProfileId(profileId);
    }

    @GetMapping("/{id}/{profileId}")
    public StoreDTO findStore(@PathVariable Long id, @PathVariable Long profileId) {
        return storeService.findStoresByIdAndProfileId(id, profileId);
    }

    @PostMapping()
    public StoreDTO saveStore(@RequestBody StoreDTO storeDTO)  {
        return storeService.saveStore(storeDTO);
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<?> deleteAllStores(@PathVariable Long profileId) {
        storeService.deleteAllByProfileId(profileId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/{profileId}")
    public ResponseEntity<?> deleteStore(@PathVariable Long id, @PathVariable Long profileId) {
        storeService.deleteByIdAndProfileId(id, profileId);
        return ResponseEntity.ok().build();
    }
}
