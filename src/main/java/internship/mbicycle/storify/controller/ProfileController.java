package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.dto.ProfileDTO;
import internship.mbicycle.storify.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/profile")
@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{id}")
    public ProfileDTO getProfileById(@PathVariable Long id) {
        return this.profileService.getById(id);
    }

    @PutMapping("/update/{id}")
    public ProfileDTO updateProfile(@PathVariable Long id, @RequestBody ProfileDTO profileDTO) {
        return this.profileService.updateProfile(id, profileDTO);
    }

}
