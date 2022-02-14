package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.model.Token;
import internship.mbicycle.storify.repository.StorifyUserRepository;
import internship.mbicycle.storify.service.MailService;
import internship.mbicycle.storify.service.StorifyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static internship.mbicycle.storify.util.EmailMessage.ACTIVATION_GREETING;
import static internship.mbicycle.storify.util.ExceptionMessage.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class StorifyUserServiceImpl implements StorifyUserService {

    private final PasswordEncoder passwordEncoder;
    private final StorifyUserRepository userRepository;
    private final MailService mailService;

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
        String message = String.format(ACTIVATION_GREETING, storifyUser.getName(), storifyUser.getActivationCode());
        mailService.send(storifyUser.getEmail(), "Activation code", message);
        return userRepository.save(storifyUser);
    }

    @Override
    public StorifyUser getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(String.format(NOT_FOUND, id)));
    }

    @Override
    public List<StorifyUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public StorifyUser getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(NOT_FOUND, email)));
    }

    @Override
    public StorifyUser getUserByActivationCode(String code) {
        return userRepository.findByActivationCode(code).orElseThrow(
                () -> new UsernameNotFoundException(String.format(NOT_FOUND, code)));
    }

    @Override
    public StorifyUser activateUserByEmail(String code) {
        StorifyUser storifyUser = getUserByActivationCode(code);
        storifyUser.setActivationCode(null);
        storifyUser.setRole("ROLE_USER");
        return storifyUser;
    }
}
