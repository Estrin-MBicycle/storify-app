package internship.mbicycle.storify.unit;

import internship.mbicycle.storify.exception.StorifyUserNotFoundException;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.repository.CartRepository;
import internship.mbicycle.storify.repository.StorifyUserRepository;
import internship.mbicycle.storify.service.MailService;
import internship.mbicycle.storify.service.StorifyUserService;
import internship.mbicycle.storify.service.impl.StorifyUserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.awt.*;
import java.util.Optional;

import static internship.mbicycle.storify.util.ExceptionMessage.NOT_FOUND_USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class StorifyUserServiceImplTest {

}