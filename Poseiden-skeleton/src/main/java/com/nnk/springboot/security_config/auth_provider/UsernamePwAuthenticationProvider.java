package com.nnk.springboot.security_config.auth_provider;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.user_service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Customize Authentication Provider
 * @author Subhi
 */
@Component
public class UsernamePwAuthenticationProvider implements AuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(UsernamePwAuthenticationProvider.class);
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public UsernamePwAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.debug("This authenticate(from UsernamePwdAuthenticationProvider) starts here.");

        // After authentication, system provide again the username
        String username = authentication.getName();
        // And get the password in plein text
        String pwd = authentication.getCredentials().toString();
        // And get the authorities of user is authenticated
        Optional<User> user = userService.findByUsername(username);
        if(user.isPresent()) {
            if(passwordEncoder.matches(pwd, user.get().getPassword())) {
                List<GrantedAuthority> authorities = new ArrayList<>();
                // Roles separated form a commas
               authorities.addAll(Arrays.stream(user.get()
                                .getRole()
                                .split(","))
                        .map(SimpleGrantedAuthority::new) // new SimpleGrantedAuthority(user.getRole())
                        .toList());

               return new UsernamePasswordAuthenticationToken(username, pwd, authorities);

            } else {
                throw new BadCredentialsException("Invalid password!");
            }
        } else {
            throw new BadCredentialsException("No User registered with this credentials");
        }

    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        return authenticationType.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
