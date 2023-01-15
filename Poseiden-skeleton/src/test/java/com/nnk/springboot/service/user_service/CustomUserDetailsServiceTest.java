package com.nnk.springboot.service.user_service;

import com.nnk.springboot.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {
    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;
    @Mock
    private UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;

    private User user;
    @BeforeEach
    public void setUp() {
        user = new User(5, "userTest", "userFullName", passwordEncoder.encode("12345"),"ADMIN");
    }
    @Test
    void loadUserByUsername() {
        when(userService.findByUsername("userTest")).thenReturn(Optional.ofNullable(user));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("userTest");

        assertThat(user.getUsername()).isEqualTo(userDetails.getUsername());
        assertThat(user.getPassword()).isEqualTo(userDetails.getPassword());
        assertThat(userDetails.getAuthorities().size()).isEqualTo(1);

    }

    @Test
    void loadUserByUsernameFailedShouldThrowException() {
        when(userService.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(()-> customUserDetailsService.loadUserByUsername(anyString()));
    }
}