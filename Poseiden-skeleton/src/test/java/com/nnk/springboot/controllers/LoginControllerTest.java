package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.user_service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = LoginController.class)
class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;

    private List<User> userList;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        userList = new ArrayList<>();
    }
    @Test
    void getAllUserArticles() throws Exception {
        when(userService.getUserList()).thenReturn(userList);

        mockMvc.perform(get("/secure/article-details"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", userList))
                .andExpect(view().name("user/list"));

    }

    @Test
    void error() throws Exception {
        String errorMessage = "You are not authorized for the requested data.";
        mockMvc.perform(get("/403"))
                .andExpect(model().attribute("errorMsg", errorMessage))
                .andExpect(status().isOk())
                .andExpect(view().name("403"));
    }
}