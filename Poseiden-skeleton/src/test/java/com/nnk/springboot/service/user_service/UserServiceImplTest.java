package com.nnk.springboot.service.user_service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder encoder;

    private List<User> userList;
    private User user1;
    private User user2;
    @BeforeEach
    void init() {
        user1 = new User(2, "user", encoder.encode("12345"), "newUser", "USER");
        user2= new User(5, "admin", encoder.encode("12345"), "newAdmin", "ADMIN");
    }
    @Test
    void getUserList() {
        userList = new ArrayList<>(List.of(user1, user2));
        when(userRepository.findAll()).thenReturn(userList);

        List<User> users = userService.getUserList();

        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    void saveNewUser() {
        User newUser = new User();
        newUser.setPassword(encoder.encode("12345"));
        newUser.setFullname("newUseFullName");
        newUser.setUsername("newUserName");
        newUser.setRole("USER");

        when(userRepository.save(any())).thenReturn(newUser);

        User userSaved = userService.saveNewUser(newUser);

        assertThat(userSaved.getUsername()).isEqualTo("newUserName");
    }

    @Test
    void getUserById() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user1));

        User userById = userService.getUserById(2);

        assertThat(userById.getUsername()).isEqualTo("user");
    }

    @Test
    void updateUser() {
        when(userRepository.save(any())).thenReturn(user2);

        user2.setUsername("admin_updated");
        user2.setPassword(encoder.encode("newPassword"));
        userService.updateUser(user2);

        assertThat(user2.getUsername()).isEqualTo("admin_updated");
    }

    @Test
    void deleteUserById() {

        when(userRepository.findById(2)).thenReturn(Optional.of(user1));

        doNothing().when(userRepository).deleteById(2);

        userService.deleteUserById(2);

        verify(userRepository, times(1)).deleteById(anyInt());
        verify(userRepository, times(1)).findById(anyInt());
        assertThat(userService.getUserList().size()).isEqualTo(0);
        //TODO: why?
        //assertThatThrownBy(() -> userService.getUserById(2));
       // assertThat(userService.getUserById(2)).isNull();

    }

    @Test
    void findByUsername() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user1));

        Optional<User> userByUsername = userService.findByUsername("user");

        assertThat(userByUsername.get().getUsername()).isEqualTo("user");
    }
}