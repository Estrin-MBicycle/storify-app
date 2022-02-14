package internship.mbicycle.storify.service;

import internship.mbicycle.storify.model.StorifyUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StorifyUserService {

    StorifyUser saveUser(StorifyUser storifyUser);

    StorifyUser getUserById(long id);

    List<StorifyUser> getAllUsers();

    void updateStorifyUser(StorifyUser storifyUser);

    StorifyUser getUserByEmail(String email);

    StorifyUser getUserByActivationCode(String code);

    StorifyUser activateUserByEmail(String code);
}
