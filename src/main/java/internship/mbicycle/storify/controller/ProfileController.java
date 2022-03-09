package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.dto.ProfileDTO;
import internship.mbicycle.storify.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/profile")
@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("")
    public ProfileDTO getProfile(@ApiIgnore Principal principal) {
        return profileService.getByEmail(principal.getName());
    }

    @PutMapping("")
    public ProfileDTO updateProfile(@Valid @RequestBody ProfileDTO profileDTO, @ApiIgnore Principal principal) {
        return profileService.updateProfileByEmail(principal.getName(), profileDTO);
    }

}
