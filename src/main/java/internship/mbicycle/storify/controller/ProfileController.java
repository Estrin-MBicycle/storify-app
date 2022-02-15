package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.dto.ProfileDTO;
import internship.mbicycle.storify.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/profile/{id}")
    public ProfileDTO getById(@PathVariable long id) {
        return this.profileService.getById(id);
    }

    @PutMapping("/profile/update/{id}")
    public ProfileDTO update(@PathVariable long id, @RequestBody ProfileDTO profileDTO) {
        return this.profileService.update(id, profileDTO);
    }

}
