package internship.mbicycle.storify.service;

import internship.mbicycle.storify.dto.StorifyUserDTO;
import internship.mbicycle.storify.model.StorifyUser;
import org.springframework.stereotype.Service;

@Service
public interface StorifyUserService {

    StorifyUser saveNewUser(StorifyUserDTO storifyUserDTO);

    StorifyUser getUserByEmail(String email);

    StorifyUser getUserByActivationCode(String code);

    StorifyUser activateUserByEmail(String code);

    StorifyUser updateEmail(String newEmail, String code, String email);

    void sendConfirmationEmail(String name);

    void saveRefreshToken(StorifyUser storifyUser, String jwtToken, String userAgent);

    StorifyUser getStorifyUserByTokenAndUserAgent(String token, String userAgent);
}
