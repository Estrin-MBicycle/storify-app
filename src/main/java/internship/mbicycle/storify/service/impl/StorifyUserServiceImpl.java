package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.converter.StorifyUserConverter;
import internship.mbicycle.storify.dto.StorifyUserDTO;
import internship.mbicycle.storify.exception.StorifyUserNotFoundException;
import internship.mbicycle.storify.exception.UserAlreadyExistsException;
import internship.mbicycle.storify.model.Cart;
import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.model.StorifyUser;
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
import static internship.mbicycle.storify.util.ExceptionMessage.USER_ALREADY_EXIST;

@Service
@RequiredArgsConstructor
@Transactional
public class StorifyUserServiceImpl implements StorifyUserService {

    private final PasswordEncoder passwordEncoder;
    private final StorifyUserRepository userRepository;
    private final MailService mailService;
    private final CartRepository cartRepository;
    private final StorifyUserConverter userConverter;

    @Override
    public void updateStorifyUser(StorifyUser storifyUser) {
        userRepository.save(storifyUser);
    }

    @Override
    public StorifyUser saveNewUser(StorifyUserDTO storifyUserDTO) {
        userRepository.findByEmail(storifyUserDTO.getEmail()).ifPresent(user -> {
            throw new UserAlreadyExistsException(String.format(USER_ALREADY_EXIST, storifyUserDTO.getEmail()));
        });
        StorifyUser storifyUser = userConverter.convertStorifyUserDTOToStorifyUser(storifyUserDTO);
        storifyUser.setRole(ROLE_USER);
        storifyUser.setPassword(passwordEncoder.encode(storifyUser.getPassword()));
        storifyUser.setActivationCode(UUID.randomUUID().toString());
        storifyUser.setProfile(new Profile());
        storifyUser.getProfile().setName(storifyUser.getName());
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
    public StorifyUser updateEmail(String newEmail, String code, String email) {
        userRepository.findByEmail(newEmail).ifPresent(user -> {
            throw new UserAlreadyExistsException(String.format(USER_ALREADY_EXIST, newEmail));
        });
        StorifyUser storifyUser = getUserByEmail(email);
        if (!storifyUser.getTempConfirmCode().equals(code)) {
            throw new StorifyUserNotFoundException(String.format(NOT_FOUND_USER, code));
        }
        storifyUser.setTempConfirmCode(null);
        storifyUser.setEmail(newEmail);
        return storifyUser;
    }

    @Override
    public void sendConfirmationEmail(String email) {
        StorifyUser storifyUser = getUserByEmail(email);
        storifyUser.setTempConfirmCode(UUID.randomUUID().toString());
        String message = String.format(CONFIRMATION_EMAIL, storifyUser.getName(), storifyUser.getTempConfirmCode());
        mailService.send(storifyUser.getEmail(), CONFIRMATION_CODE, message);
    }

    @Override
    public void saveJwtToken(StorifyUser storifyUser, String jwtToken, String userAgent) {
        storifyUser.getTokenMap().put(userAgent, jwtToken);
        userRepository.save(storifyUser);
    }

    public StorifyUser getStorifyUserByTokenAndUserAgent(String token, String userAgent) {
        return userRepository.findStorifyUserByJwtTokenAndUserAgent(token, userAgent)
                .orElseThrow(() -> new StorifyUserNotFoundException(String.format(NOT_FOUND_USER, token)));
    }


}
