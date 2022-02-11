package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.model.Token;
import internship.mbicycle.storify.repository.StorifyUserRepository;
import internship.mbicycle.storify.service.MailSenderService;
import internship.mbicycle.storify.service.StorifyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class StorifyUserServiceImpl implements StorifyUserService {

    private final PasswordEncoder passwordEncoder;
    private final StorifyUserRepository userRepository;
    private final MailSenderService mailSenderService;

    @Override
    public void updateStorifyUser(StorifyUser storifyUser) {
        userRepository.save(storifyUser);
    }

    @Override
    public StorifyUser saveUser(StorifyUser storifyUser) {
        storifyUser.setRole("ROLE_GUEST");
        storifyUser.setPassword(passwordEncoder.encode(storifyUser.getPassword()));
        storifyUser.setActivationCode(UUID.randomUUID().toString());
        storifyUser.setToken(new Token());
        String message = String.format(
                "Hello, %s! \n " +
                        "Welcome to Storify. Please, visit next link: http://localhost:8080/activate/%s",
                storifyUser.getName(), storifyUser.getActivationCode()
        );
        mailSenderService.send(storifyUser.getEmail(), "Activation code", message);
        return userRepository.save(storifyUser);
    }

    @Override
    public StorifyUser getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found in db"));
    }

    @Override
    public List<StorifyUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public StorifyUser activateEmail(String code) {
        StorifyUser storifyUser = userRepository.findByActivationCode(code)
                .orElseThrow(() -> new UsernameNotFoundException("User not found in database with it activation code"));
        storifyUser.setActivationCode(null);
        storifyUser.setRole("ROLE_USER");
        return userRepository.save(storifyUser);
    }

    @Override
    public StorifyUser getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found in db"));
    }
}
