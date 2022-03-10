package internship.mbicycle.storify.service.impl;

import internship.mbicycle.storify.exception.TokenNotFoundException;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.repository.StorifyUserRepository;
import internship.mbicycle.storify.service.StorifyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static internship.mbicycle.storify.util.ExceptionMessage.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final StorifyUserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        StorifyUser storifyUser = userService.getUserByEmail(email);
        return User.builder()
                .username(storifyUser.getUsername())
                .password(storifyUser.getPassword())
                .authorities(storifyUser.getAuthorities())
                .disabled(!storifyUser.isEnabled())
                .build();
    }
}
