package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.exception.StorifyUserNotFoundException;
import internship.mbicycle.storify.model.Cart;
import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.model.Token;
import internship.mbicycle.storify.repository.CartRepository;
import internship.mbicycle.storify.repository.StorifyUserRepository;
import internship.mbicycle.storify.service.MailService;
import internship.mbicycle.storify.service.StorifyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static internship.mbicycle.storify.util.Constants.ROLE_USER;
import static internship.mbicycle.storify.util.EmailMessage.*;
import static internship.mbicycle.storify.util.ExceptionMessage.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
@Transactional
public class StorifyUserServiceImpl implements StorifyUserService {

    private final PasswordEncoder passwordEncoder;
    private final StorifyUserRepository userRepository;
    private final MailService mailService;
    private final CartRepository cartRepository;

    @Override
    public void updateStorifyUser(StorifyUser storifyUser) {
        userRepository.save(storifyUser);
    }

    @Override
    public StorifyUser saveNewUser(StorifyUser storifyUser) {
        storifyUser.setRole(ROLE_USER);
        storifyUser.setPassword(passwordEncoder.encode(storifyUser.getPassword()));
        storifyUser.setActivationCode(UUID.randomUUID().toString());
        storifyUser.setToken(new Token());
        storifyUser.setProfile(new Profile());
        Cart cart = new Cart();
        cartRepository.save(cart);
        storifyUser.getProfile().setCart(cart);
        String message = String.format(ACTIVATION_GREETING, storifyUser.getName(), storifyUser.getActivationCode());
        mailService.send(storifyUser.getEmail(), REGISTRATION_CONFIRMATION_CODE, message);
        return userRepository.save(storifyUser);
    }

    @Override
    public StorifyUser getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new StorifyUserNotFoundException(String.format(NOT_FOUND_USER, email)));
    }

    @Override
    public StorifyUser getUserByActivationCode(String code) {
        return userRepository.findByActivationCode(code).orElseThrow(
                () -> new StorifyUserNotFoundException(String.format(NOT_FOUND_USER, code)));
    }

    @Override
    public StorifyUser activateUserByEmail(String code) {
        StorifyUser storifyUser = getUserByActivationCode(code);
        storifyUser.setActivationCode(null);
        return storifyUser;
    }

    @Override
    public void updateEmail(String newEmail, String code, String email) {
        StorifyUser storifyUser = getUserByEmail(email);
        if (!storifyUser.getTempConfirmCode().equals(code)) {
            throw new StorifyUserNotFoundException(String.format(NOT_FOUND_USER, code));
        }
        storifyUser.setEmail(newEmail);
    }

    @Override
    public void sendConfirmationEmail(String email) {
        StorifyUser storifyUser = getUserByEmail(email);
        storifyUser.setTempConfirmCode(UUID.randomUUID().toString());
        String message = String.format(CONFIRMATION_EMAIL, storifyUser.getName(), storifyUser.getTempConfirmCode());
        mailService.send(storifyUser.getEmail(), CONFIRMATION_CODE, message);
    }
}
