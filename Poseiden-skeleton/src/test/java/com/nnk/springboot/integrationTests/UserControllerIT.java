package com.nnk.springboot.integrationTests;

import com.nnk.springboot.test_repository.UserTestH2Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserTestH2Repository userTestH2Repository;

    @Autowired
    private PasswordEncoder encoder;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    @WithMockUser(value = "admin", authorities = {"ADMIN"})
    //@WithUserDetails("userAdmin")
    @Sql(statements = "INSERT INTO users (id,username, password, fullname, role) values (5, 'userAdmin', '$2a$12$VGzl7qsO5Bmw3S2ZXkSB4eKiL25fczrZcmRtLnSzCKkglizLFQ3MG', 'userFullName', 'ADMIN')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM users WHERE id = 5", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUserTest_shouldReturnUsersView() throws Exception {
        mockMvc.perform(get("/user/list").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("user/list"));

    assertThat(userTestH2Repository.findAll().size()).isEqualTo(1);
    }
    @Test
    void getUserTestAccessDenied_shouldReturn403() throws Exception {
        mockMvc.perform(get("/user/list").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());

    }
    @Test
    @WithMockUser(value = "admin", authorities = {"ADMIN"})
    public void addUserFormPage() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    @WithMockUser(value = "admin", authorities = {"ADMIN"})
    @Sql(statements = "DELETE FROM users", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addUserSuccessShouldReturnUserListPage() throws Exception {

        mockMvc.perform(post("/user/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("username", "newUser")
                .param("fullname", "nameLastName")
                .param("password", encoder.encode("12345"))
                .param("role", "USER")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));

        assertThat(userTestH2Repository.findAll().size()).isEqualTo(1);
    }


    @Test
    @WithMockUser(value = "admin", authorities = {"ADMIN"})
    @Sql(statements = "DELETE FROM users", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM users", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addUserFailedShouldReturnUserAddPage() throws Exception {

        mockMvc.perform(post("/user/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("username", "")
                        .param("fullname", "nameLastName")
                        .param("password", encoder.encode("12345"))
                        .param("role", "USER")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));

        assertThat(userTestH2Repository.findAll().size()).isEqualTo(0);
    }

    @Test
    @WithUserDetails("userAdmin")
    @Sql(statements = "INSERT INTO users (id,username, password, fullname, role) values (3, 'userAdmin', '$2a$12$VGzl7qsO5Bmw3S2ZXkSB4eKiL25fczrZcmRtLnSzCKkglizLFQ3MG', 'userFullName', 'ADMIN')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM users WHERE id = 3", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addUserFailedWithUsernameAlreadyExistShouldReturnUserAddPage() throws Exception {

        mockMvc.perform(post("/user/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("username", "userAdmin")
                        .param("fullname", "nameLastName")
                        .param("password", encoder.encode("12345"))
                        .param("role", "USER")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userExist"))
                .andExpect(view().name("user/add"));

        assertThat(userTestH2Repository.findById(3).get().getFullname()).isEqualTo("userFullName");
    }
    @Test
    @WithMockUser(value = "admin", authorities = {"ADMIN"})
    @Sql(statements = "INSERT INTO users (id,username, password, fullname, role) values (4, 'ishanch', '$2a$12$VGzl7qsO5Bmw3S2ZXkSB4eKiL25fczrZcmRtLnSzCKkglizLFQ3MG', 'ishanch_umid', 'USER')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM users WHERE id = 4", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void showUserUpdateFormPage() throws Exception {
        mockMvc.perform(get("/user/update/{id}" ,4))
                .andExpect(model().attributeExists("user"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }
    @Test
    @WithMockUser(value = "admin", authorities = {"ADMIN"})
    @Sql(statements = "INSERT INTO users (id,username, password, fullname, role) values (5, 'ishanch', '$2a$12$VGzl7qsO5Bmw3S2ZXkSB4eKiL25fczrZcmRtLnSzCKkglizLFQ3MG', 'ishanch_umid', 'USER')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM users WHERE id = 5", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateUserSuccessShouldReturnUserListPage() throws Exception {

        mockMvc.perform(post("/user/update/{id}", 5)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "updateIshanch")
                .param("fullname", "ishach_umidUpdated")
                .param("password", encoder.encode("12345"))
                .param("role", "ADMIN")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));

        assertThat(userTestH2Repository.findById(5).get().getUsername()).isEqualTo("updateIshanch");
    }

    @Test
    @WithMockUser(value = "admin", authorities = {"ADMIN"})
    @Sql(statements = "INSERT INTO users (id,username, password, fullname, role) values (6, 'ishanch', '$2a$12$VGzl7qsO5Bmw3S2ZXkSB4eKiL25fczrZcmRtLnSzCKkglizLFQ3MG', 'ishanch_umid', 'USER')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM users WHERE id = 6", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateUserSuccessShouldWithSameUsernameAsBeforeReturnUserListPage() throws Exception {

        mockMvc.perform(post("/user/update/{id}", 6)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username", "ishanch")
                        .param("fullname", "ishach_umidUpdated")
                        .param("password", encoder.encode("12345"))
                        .param("role", "ADMIN")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));

        assertThat(userTestH2Repository.findById(6).get().getUsername()).isEqualTo("ishanch");
        assertThat(userTestH2Repository.findById(6).get().getRole()).isEqualTo("ADMIN");
    }

    @Test
    @WithUserDetails(value = "admin")
    @Sql(statements = "INSERT INTO users (id,username, password, fullname, role) values (6, 'ishanch', '$2a$12$VGzl7qsO5Bmw3S2ZXkSB4eKiL25fczrZcmRtLnSzCKkglizLFQ3MG', 'ishanch_umid', 'USER'), (1, 'admin', '$2a$12$VGzl7qsO5Bmw3S2ZXkSB4eKiL25fczrZcmRtLnSzCKkglizLFQ3MG', 'adminUser', 'ADMIN' )", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM users WHERE id = 6 and id = 1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateUserFailedWithUsernameAlreadyTakenReturnUserAddPage() throws Exception {

        mockMvc.perform(post("/user/update/{id}", 6)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username", "admin")
                        .param("fullname", "ishach_umidUpdated")
                        .param("password", encoder.encode("12345"))
                        .param("role", "ADMIN")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));

        assertThat(userTestH2Repository.findById(6).get().getUsername()).isEqualTo("ishanch");
        assertThat(userTestH2Repository.findById(6).get().getRole()).isEqualTo("USER");
    }

    @Test
    @WithMockUser(value = "admin", authorities = {"ADMIN"})
    @Sql(statements = "INSERT INTO users (id,username, password, fullname, role) values (8, 'ishanch', '$2a$12$VGzl7qsO5Bmw3S2ZXkSB4eKiL25fczrZcmRtLnSzCKkglizLFQ3MG', 'ishanch_umid', 'USER')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM users WHERE id = 8", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteUserExistsShouldReturnUserListPage() throws Exception {
        mockMvc.perform(get("/user/delete/{id}", 8))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));
    }

}
