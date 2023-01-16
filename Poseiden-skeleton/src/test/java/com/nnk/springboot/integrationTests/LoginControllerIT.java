package com.nnk.springboot.integrationTests;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.user_service.UserService;
import com.nnk.springboot.test_repository.UserTestH2Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class LoginControllerIT {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserService userService;

    @Autowired
    private UserTestH2Repository userTestH2Repository;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }


    @Test
    void loginTest() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(content().string(containsString("Login with Github")));

    }

    @Test
    @WithUserDetails("userAdmin")
    @Sql(statements = "INSERT INTO users (id,username, password, fullname, role) values (3, 'userAdmin', '$2a$12$97hRulpibn96YXW04fWf8e3ywDI9G4bkPXgB3KQUYVLi2g/eGayZS', 'userFullName', 'ADMIN')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM users WHERE id = 3", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void loginValideTest() throws Exception {
        mockMvc.perform(get("/login")
                        .param("username", "userAdmin")
                        .param("password", "Subhy7!")
                        .with(csrf()))

                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

    }



    @Test
    //@WithMockUser(value = "admin", authorities = {"ADMIN"})
    @WithUserDetails("userAdmin")
    @Sql(statements = "INSERT INTO users (id,username, password, fullname, role) values (3, 'userAdmin', '$2a$12$VGzl7qsO5Bmw3S2ZXkSB4eKiL25fczrZcmRtLnSzCKkglizLFQ3MG', 'userFullName', 'ADMIN')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM users WHERE id = 3", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllUserArticles() throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        mockMvc.perform(get("/secure/article-details")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(model().attributeExists("users"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"));

        List<User> users = userService.getUserList();

        assertThat(users.size()).isEqualTo(1);
        assertThat(encoder.matches("12345", userTestH2Repository.findById(3).get().getPassword())).isTrue();
    }

    @Test
    public void getAllUserArticlesAccessDenied() throws Exception {
        mockMvc.perform(get("/secure/article-details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());

    }
    @Test
    @WithMockUser("UserTest")
    public void shouldReturnErrorPageWhenUserHasNoAuthority() throws Exception {
        mockMvc.perform(get("/403"))
                .andExpect(model().attributeExists("errorMsg"))
                .andExpect(status().isOk())
                .andExpect(view().name("403"));
    }


}
