package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.jpa.StorifyUserRepository;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.service.MailSenderService;
import internship.mbicycle.storify.service.StorifyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class StorifyUserServiceImpl implements StorifyUserService, UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final StorifyUserRepository userRepository;
    private final MailSenderService mailSenderService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<StorifyUser> optionalStorifyUser = userRepository.findByEmail(email);
        if (optionalStorifyUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found in database");
        }
        StorifyUser storifyUser = optionalStorifyUser.get();
        return convertStorifyUserIntoUserDetails(storifyUser);
    }

    public User convertStorifyUserIntoUserDetails(StorifyUser storifyUser) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(storifyUser.getRole()));
        return new User(storifyUser.getEmail(), storifyUser.getPassword(), authorities);
    }

    @Override
    public StorifyUser saveUser(StorifyUser storifyUser) {
        storifyUser.setRole("ROLE_GUEST");
        storifyUser.setPassword(passwordEncoder.encode(storifyUser.getPassword()));
        storifyUser.setActivationCode(UUID.randomUUID().toString());
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
        Optional<StorifyUser> optionalStorifyUser = userRepository.findById(id);
        if (optionalStorifyUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found in database");
        }
        return optionalStorifyUser.get();
    }

    @Override
    public List<StorifyUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean activateEmail(String code) {
        Optional<StorifyUser> optionalStorifyUser = userRepository.findByActivationCode(code);
        if (optionalStorifyUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found in database");
        }
        StorifyUser storifyUser = optionalStorifyUser.get();
        storifyUser.setActivationCode(null);
        storifyUser.setRole("ROLE_USER");
        userRepository.save(storifyUser);
        return true;
    }

    @Override
    public StorifyUser getUserByEmail(String email) {
        Optional<StorifyUser> optionalStorifyUser = userRepository.findByEmail(email);
        if (optionalStorifyUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found in database");
        }
        return optionalStorifyUser.get();
    }
}
