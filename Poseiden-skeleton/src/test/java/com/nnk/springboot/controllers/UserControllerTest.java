package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.user_service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;

    private List<User> userList;

    @BeforeEach
    void init() {
        User newUser = new User();
        newUser.setFullname("UserFullName");
        newUser.setRole("ADMIN");
        newUser.setUsername("UserName");

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        userList = new ArrayList<>(List.of(newUser));
    }
    @Test
    @WithMockUser
    void home() throws Exception {
        when(userService.getUserList()).thenReturn(userList);

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", userList))
                .andExpect(view().name("user/list"));
    }

    @Test
    @WithMockUser
    void addUserForm() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    @WithMockUser
    void validate() throws Exception {
        User newUser = new User();
        newUser.setFullname("UserFullname");
        newUser.setRole("USER");
        newUser.setUsername("Username");
        newUser.setPassword("12345");

        when(userService.saveNewUser(newUser)).thenReturn(newUser);

        mockMvc.perform(post("/user/validate")
                .param("fullname", "UserFullname")
                .param("username", "Username")
                .param("password", "12345")
                .param("role", "USER")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));


    }
    @Test
    @WithMockUser
    void validateUserByExistingUsername() throws Exception {
        User newUser = new User();
        newUser.setFullname("NewUserFullname");
        newUser.setRole("ADMIN");
        newUser.setUsername("UsernameExisting");
        newUser.setPassword("12345678");

        when(userService.findByUsername(anyString())).thenReturn(Optional.of(newUser));

        mockMvc.perform(post("/user/validate")
                        .param("fullname", "newFullname")
                        .param("role", "ADMIN")
                        .param("password", "12345678")
                        .param("username", "UsernameExisting")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("userExist", "Username already taken"))
                .andExpect(view().name("user/add"));
    }

    @Test
    @WithMockUser
    void validateFailed() throws Exception {
        mockMvc.perform(post("/user/validate"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    @WithMockUser
    void showUpdateForm() throws Exception {
        User updateUser = new User();
        updateUser.setFullname("UserFullnameUpdate");
        updateUser.setRole("USER");
        updateUser.setUsername("UsernameUpdate");
        updateUser.setPassword("");


        when(userService.getUserById(7)).thenReturn(updateUser);

        mockMvc.perform(get("/user/update/{id}", 7))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", updateUser))
                .andExpect(view().name("user/update"));

    }

    @Test
    @WithMockUser
    void updateUserFailed() throws Exception {
        mockMvc.perform(post("/user/update/{id}", 8))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }
    @Test
    @WithMockUser
    void updateUser() throws Exception {
        User updateUser = new User();
        updateUser.setId(5);
        updateUser.setFullname("UserFullnameUpdate");
        updateUser.setRole("USER");
        updateUser.setUsername("UsernameUpdate");
        updateUser.setPassword("12345678");

        when(userService.findByUsername(updateUser.getUsername())).thenReturn(null);

        doNothing().when(userService).updateUser(updateUser);

        mockMvc.perform(post("/user/update/{id}", 5)
                .param("fullname", "UserFullname")
                .param("username", "Username")
                .param("password", "12345")
                .param("role", "USER")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));


    }
    @Test
    @WithMockUser
    void updateUserByAlreadyExistingUsernameFailed() throws Exception {
        User updateUser = new User();
        updateUser.setId(6);
        updateUser.setFullname("UserFullnameUpdate");
        updateUser.setRole("ADMIN");
        updateUser.setUsername("anyUser");
        updateUser.setPassword("12345678");

        User userExist = new User();
        userExist.setId(7);
        userExist.setFullname("userExist");
        userExist.setRole("USER");
        userExist.setUsername("UsernameExisting");
        userExist.setPassword("12345678");

        when(userService.getUserById(6)).thenReturn(updateUser);
        when(userService.findByUsername(anyString())).thenReturn(Optional.of(userExist));


        mockMvc.perform(post("/user/update/{id}",6)
                        .param("fullname","updateFullName")
                        .param("role","USER")
                        .param("password", "12345")
                        .param("username", "anyUser")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("userExist", "Username already taken by other User"))
                .andExpect(view().name("user/update"));

    }

    @Test
    @WithMockUser
    void deleteUser() throws Exception {

        doNothing().when(userService).deleteUserById(anyInt());

        mockMvc.perform(get("/user/delete/{id}", 5))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));
    }
}