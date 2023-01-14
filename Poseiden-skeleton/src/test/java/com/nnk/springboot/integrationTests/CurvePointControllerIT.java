package com.nnk.springboot.integrationTests;

import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.test_repository.CurvePointTestH2Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CurvePointControllerIT {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private CurvePointTestH2Repository curvePointTestH2Repository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    @WithMockUser(value = "UserTest", authorities = {"USER", "ADMIN"})
    @Sql(statements = "INSERT INTO curvepoint (id, curveid, term, value) values (3, 15, 254.0, 36.89),(5, 87, 745.0, 105.36);", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM curvepoint WHERE id = 3 AND id = 5", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllCurvePointsShouldReturnCurvePointListPage() throws Exception {
        mockMvc.perform(get("/api/curvePoint/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("curvePointList"));
        assertThat(curvePointTestH2Repository.findAll().size()).isEqualTo(2);
    }

    @Test
    @WithMockUser("UserTest")
    public void addCurvePointFormShouldReturnCurvePointAddPage() throws Exception {
        mockMvc.perform(get("/api/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "DELETE FROM curvepoint", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM curvepoint", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addNewCurvePointShouldReturnCurvePointListPage() throws Exception {
        CurvePointDTO curvePointDTO = new CurvePointDTO(5, 89.25, 658.4);
        mockMvc.perform(post("/api/curvePoint/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("curveId", String.valueOf(curvePointDTO.getCurveId()))
                        .param("term", String.valueOf(curvePointDTO.getTerm()))
                        .param("value", String.valueOf(curvePointDTO.getValue()))
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/curvePoint/list"));

        assertThat(curvePointTestH2Repository.findAll().size()).isEqualTo(1);
        assertThat(curvePointTestH2Repository.findAll()).isNotNull();
    }

    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "DELETE FROM curvepoint", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void addNewCurvePointFailedShouldReturnCurvePointAddPage() throws Exception {
        mockMvc.perform(post("/api/curvePoint/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));

        assertThat(curvePointTestH2Repository.findAll().size()).isEqualTo(0);
    }
    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "INSERT INTO curvepoint (id, curveid, term, value) values (4, 35, 521.2, 65.24),(6, 41, 128.0, 653.2)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM curvepoint WHERE id = 4 AND id = 6", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void showCurvePointUpdateFormPage() throws Exception {

        mockMvc.perform(get("/api/curvePoint/update/{id}", 4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("curvePointDTO"))
                .andExpect(view().name("curvePoint/update"));
    }
    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "INSERT INTO curvepoint (id, curveid, term, value) values (4, 35, 521.2, 65.24),(6, 41, 128.0, 653.2);", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM curvepoint WHERE id = 4 AND id = 6", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateCurvePointShouldReturnCurvePointListPage() throws Exception {
        CurvePointDTO curvePointDTO = new CurvePointDTO(5, 89.25, 658.4);
        curvePointDTO.setCurveid(4);

        mockMvc.perform(post("/api/curvePoint/update/{id}", 4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("curveId", String.valueOf(curvePointDTO.getCurveId()))
                        .param("term", String.valueOf(curvePointDTO.getTerm()) )
                        .param("value", String.valueOf(curvePointDTO.getValue()))
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/curvePoint/list"));

        assertThat(curvePointTestH2Repository.findById(4).get().getTerm()).isEqualTo(89.25);
    }

    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "INSERT INTO curvepoint (id, curveid, term, value) values (4, 35, 521.2, 65.24),(6, 41, 128.0, 653.2);", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM curvepoint WHERE id = 4 AND id = 6", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateCurvePointFailedShouldReturnCurvePointUpdatePage() throws Exception {
        mockMvc.perform(post("/api/curvePoint/update/{id}", 4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("curveId", "")
                        .param("term", "")
                        .param("value", "")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));

        assertThat(curvePointTestH2Repository.findById(4).get().getTerm()).isEqualTo(521.2);
    }
    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "INSERT INTO curvepoint (id, curveid, term, value) values (10, 35, 521.2, 65.24),(8, 41, 128.0, 653.2);", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM curvepoint WHERE id = 10 AND id = 8", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteCurvePointExistingShouldReturnCurvePointListPage() throws Exception {
        mockMvc.perform(get("/api/curvePoint/delete/{id}", 8))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/curvePoint/list"));

        assertAll(
                () -> assertThat(curvePointTestH2Repository.findById(8)).isEmpty(),
                () -> assertThat(curvePointTestH2Repository.findAll().size()).isEqualTo(1)
        );
    }

}
