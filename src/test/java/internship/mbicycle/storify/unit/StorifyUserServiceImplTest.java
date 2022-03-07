package internship.mbicycle.storify.unit;

import internship.mbicycle.storify.converter.StorifyUserConverter;
import internship.mbicycle.storify.dto.StorifyUserDTO;
import internship.mbicycle.storify.exception.StorifyUserNotFoundException;
import internship.mbicycle.storify.model.Cart;
import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.model.Token;
import internship.mbicycle.storify.repository.CartRepository;
import internship.mbicycle.storify.repository.StorifyUserRepository;
import internship.mbicycle.storify.service.MailService;
import internship.mbicycle.storify.service.impl.StorifyUserServiceImpl;
import internship.mbicycle.storify.util.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static internship.mbicycle.storify.util.ExceptionMessage.NOT_FOUND_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StorifyUserServiceImplTest {

    private static final String EMAIL = "test@mail.ru";
    private static final String ACTIVATION_CODE = "1Q2W2E3";
    private static final String PASSWORD = "123456";
    private static final String NAME = "TEST";


    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private StorifyUserRepository userRepository;

    @Mock
    private StorifyUserConverter userConverter;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private MailService mailService;

    @InjectMocks
    private StorifyUserServiceImpl userService;

    @Test
    void shouldSaveNewUser() {
        StorifyUserDTO userDTO = StorifyUserDTO.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .build();
        StorifyUser storifyUser = StorifyUser.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .build();
        given(userConverter.convertStorifyUserDTOToStorifyUser(userDTO)).willReturn(storifyUser);
        given(passwordEncoder.encode(storifyUser.getPassword())).willReturn("secretPass");
        given(cartRepository.save(any())).willReturn(new Cart());
        doNothing().when(mailService).send(anyString(), anyString(), anyString());
        StorifyUser expected = StorifyUser.builder()
                .email(EMAIL)
                .password(passwordEncoder.encode(PASSWORD))
                .name(NAME)
                .profile(new Profile())
                .token(new Token())
                .role(Constants.ROLE_USER)
                .activationCode(ACTIVATION_CODE)
                .build();
        given(userRepository.save(storifyUser)).willReturn(expected);
        StorifyUser actual = userService.saveNewUser(userDTO);
        actual.setActivationCode(ACTIVATION_CODE);
        assertEquals(expected, actual);
    }

    @Test
    void shouldGetUserByEmail() {
        given(userRepository.findByEmail(EMAIL)).willReturn(Optional.empty());
        StorifyUserNotFoundException exception = assertThrows(StorifyUserNotFoundException.class,
                () -> userService.getUserByEmail(EMAIL));
        assertEquals(String.format(NOT_FOUND_USER, EMAIL), exception.getMessage());
        then(userRepository).should(times(1)).findByEmail(EMAIL);
        StorifyUser expected = StorifyUser.builder().email(EMAIL).build();
        given(userRepository.findByEmail(EMAIL)).willReturn(Optional.of(expected));
        StorifyUser actual = userService.getUserByEmail(EMAIL);
        assertEquals(expected, actual);
        then(userRepository).should(times(2)).findByEmail(Mockito.any());
    }

    @Test
    void shouldGetUserByActivationCode() {
        given(userRepository.findByEmail(ACTIVATION_CODE)).willReturn(Optional.empty());
        StorifyUserNotFoundException exception = assertThrows(StorifyUserNotFoundException.class,
                () -> userService.getUserByEmail(ACTIVATION_CODE));
        assertEquals(String.format(NOT_FOUND_USER, ACTIVATION_CODE), exception.getMessage());
        then(userRepository).should(times(1)).findByEmail(ACTIVATION_CODE);
        StorifyUser expected = StorifyUser.builder().email(ACTIVATION_CODE).build();
        given(userRepository.findByEmail(ACTIVATION_CODE)).willReturn(Optional.of(expected));
        StorifyUser actual = userService.getUserByEmail(ACTIVATION_CODE);
        assertEquals(expected, actual);
        then(userRepository).should(times(2)).findByEmail(Mockito.any());
    }

    @Test
    void shouldActivateUserByEmail() {
        StorifyUser storifyUser = StorifyUser.builder()
                .activationCode(ACTIVATION_CODE)
                .build();
        given(userRepository.findByActivationCode(ACTIVATION_CODE)).willReturn(Optional.of(storifyUser));
        StorifyUser expected = StorifyUser.builder()
                .activationCode(null)
                .build();
        StorifyUser actual = userService.activateUserByEmail(ACTIVATION_CODE);
        assertEquals(expected, actual);
        then(userRepository).should(times(1)).findByActivationCode(ACTIVATION_CODE);
    }
}