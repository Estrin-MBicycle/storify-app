package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.dto.ProfileDTO;
import internship.mbicycle.storify.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/profiles")
@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{id}")
    public ProfileDTO getProfileById(@PathVariable Long id) {
        return profileService.getById(id);
    }

    @PutMapping("/{id}")
    public ProfileDTO updateProfile(@PathVariable Long id, @RequestBody ProfileDTO profileDTO) {
        return profileService.updateProfile(id, profileDTO);
    }

}
