package com.nnk.springboot.service.user_service;


import com.nnk.springboot.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;


public class SecurityUser implements UserDetails {
    private static final Logger LOGGER = LogManager.getLogger(SecurityUser.class);
    private User user;

    public SecurityUser(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        LOGGER.debug("User's Role(s) is(are) added to the user authenticated(from SecurityUser)");
        //If a user have tow or plus roles, seperated with commas, like: USER, ADMIN
        return Arrays.stream(user
                        .getRole()
                        .split(","))
                .map(SimpleGrantedAuthority::new) // new SimpleGrantedAuthority(user.getRole())
                .toList();

        /* LOGGER.debug("User's Role(s) is(are) added to the user authenticated(from SecurityUser)");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return authorities;*/
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
