package com.nnk.springboot.service.user_service;

import com.nnk.springboot.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The classe implements UserDetailsService,
 * for retrieving a username(from the database), password and other attributes(ex. authorization info) for authenticating with a username and password.
 * To load the user information(customer details) during authentication process.
 * @author Subhi
 */
@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private UserService userService;
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Loading user(with username) from the database and transform the user details(ex.properties, GrantedAuthorities) with SecurityUser.
     * @param username the username identifying the user whose data is required.
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("loadUserByUsername stars here from CustomUserDetailsService");
        Optional<User> user = userService.findByUsername(username);
        return user
                .map(SecurityUser::new)
                .orElseThrow(()-> new UsernameNotFoundException("Username not found: " + username));
    }
}
