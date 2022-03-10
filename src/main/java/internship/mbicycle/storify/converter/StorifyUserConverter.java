package internship.mbicycle.storify.converter;

import internship.mbicycle.storify.dto.StorifyUserDTO;
import internship.mbicycle.storify.model.StorifyUser;
import org.springframework.stereotype.Component;

@Component
public class StorifyUserConverter {

    public StorifyUser convertStorifyUserDTOToStorifyUser(StorifyUserDTO storifyUserDTO) {
        return StorifyUser.builder()
                .email(storifyUserDTO.getEmail())
                .password(storifyUserDTO.getPassword())
                .name(storifyUserDTO.getName())
                .build();
    }
}
