package internship.mbicycle.storify.converter;

import internship.mbicycle.storify.dto.StorifyUserDTO;
import internship.mbicycle.storify.model.StorifyUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StorifyUserConverter {

    private final ProfileConverter profileConverter;

    public StorifyUser convertStorifyUserDTOToStorifyUser(StorifyUserDTO storifyUserDTO) {
        return StorifyUser.builder()
                .email(storifyUserDTO.getEmail())
                .password(storifyUserDTO.getPassword())
                .name(storifyUserDTO.getName())
                .build();
    }
}
