package com.nnk.springboot.integrationTests;

import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.test_repository.RuleNameTestH2Repository;
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
public class RuleNameControllerIT {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private RuleNameTestH2Repository ruleNameTestH2Repository;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }
    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "INSERT INTO rulename (id,name, description, json, template, sql_str, sql_part) VALUES (4, 'ruleN°1', 'description', 'json', 'template', 'sqlStr', 'sqlPart' )", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM rulename WHERE id = 4", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllRuleNamesShouldReturnRuleNameListPage() throws Exception {
        mockMvc.perform(get("/api/ruleName/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ruleNameList"));

        assertThat(ruleNameTestH2Repository.findAll().size()).isEqualTo(1);
    }

    @Test
    @WithMockUser("UserTest")
    public void addRuleNameFormShouldReturnRuleNameAddPage() throws Exception {
        mockMvc.perform(get("/api/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }
    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "DELETE FROM rulename", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addNewRuleNameShouldReturnRuleNameListPage() throws Exception {
        RuleNameDTO ruleNameDTO = new RuleNameDTO("RuleName", "RuleDescription","json", "template", "sqlStr", "sqlPart");

        mockMvc.perform(post("/api/ruleName/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("name", ruleNameDTO.getName())
                        .param("description",ruleNameDTO.getDescription())
                        .param("json", ruleNameDTO.getJson())
                        .param("template", ruleNameDTO.getTemplate())
                        .param("sqlStr", ruleNameDTO.getSqlStr())
                        .param("sqlPart", ruleNameDTO.getSqlPart())
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/ruleName/list"));

        assertThat(ruleNameTestH2Repository.findAll().size()).isEqualTo(1);
        assertThat(ruleNameTestH2Repository.findAll()).isNotNull();
    }
    @Test
    @WithMockUser("UserTest")
    public void addNewRuleNameFailedShouldReturnRuleNameAddPage() throws Exception {
        RuleNameDTO ruleNameDTO = new RuleNameDTO("RuleNameLengthMoreThan20Letters", "RuleDescription","json", "template", "sqlStr", "sqlPart");

        mockMvc.perform(post("/api/ruleName/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("name", ruleNameDTO.getName())
                        .param("description",ruleNameDTO.getDescription())
                        .param("json", ruleNameDTO.getJson())
                        .param("template", ruleNameDTO.getTemplate())
                        .param("sqlStr", ruleNameDTO.getSqlStr())
                        .param("sqlPart", ruleNameDTO.getSqlPart())
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));

        assertThat(ruleNameTestH2Repository.findAll().size()).isEqualTo(0);

    }
    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "INSERT INTO rulename (id,name, description, json, template, sql_str, sql_part) VALUES (7, 'ruleN°7', 'description7', 'json7', 'template7', 'sqlStr7', 'sqlPart7' )", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM rulename WHERE id = 7", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void showRuleNameUpdateForm() throws Exception {
        mockMvc.perform(get("/api/ruleName/update/{id}", 7)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ruleNameDTO"))
                .andExpect(view().name("ruleName/update"));
    }
    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "INSERT INTO rulename (id,name, description, json, template, sql_str, sql_part) VALUES (7, 'ruleN°7', 'description7', 'json7', 'template7', 'sqlStr7', 'sqlPart7' )", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM rulename WHERE id = 7", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateRuleNameExistingShouldReturnRuleNameListPage() throws Exception {
        RuleNameDTO ruleNameDTO = new RuleNameDTO("RuleName", "RuleDescription","json", "template", "sqlStr", "sqlPart");

        mockMvc.perform(post("/api/ruleName/update/{id}", 7)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("name", ruleNameDTO.getName())
                        .param("description",ruleNameDTO.getDescription())
                        .param("json", ruleNameDTO.getJson())
                        .param("template", ruleNameDTO.getTemplate())
                        .param("sqlStr", ruleNameDTO.getSqlStr())
                        .param("sqlPart", ruleNameDTO.getSqlPart())
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/ruleName/list"));

        assertThat(ruleNameTestH2Repository.findAll().size()).isEqualTo(1);
        assertThat(ruleNameTestH2Repository.findById(7).get().getName()).isEqualTo("RuleName");
    }

    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "INSERT INTO rulename (id,name, description, json, template, sql_str, sql_part) VALUES (7, 'ruleN°7', 'description7', 'json7', 'template7', 'sqlStr7', 'sqlPart7' )", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM rulename WHERE id = 7", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateRuleNameExistingFailedShouldReturnRuleNameUpdatePage() throws Exception {
        mockMvc.perform(post("/api/ruleName/update/{id}", 7)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("name", "")
                        .param("description","")
                        .param("json", "MoreThan20LettersNotAccepted")
                        .param("template", "")
                        .param("sqlStr", "")
                        .param("sqlPart", "")
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));

        assertThat(ruleNameTestH2Repository.findAll().size()).isEqualTo(1);
        assertThat(ruleNameTestH2Repository.findById(7).get().getName()).isEqualTo("ruleN°7");
    }
    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "INSERT INTO rulename (id,name, description, json, template, sql_str, sql_part) VALUES (8, 'ruleN°8', 'description8', 'json8', 'template8', 'sqlStr8', 'sqlPart8' )", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM rulename WHERE id = 8", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteRuleNameExistingShouldReturnRuleNameListPage() throws Exception {
        mockMvc.perform(get("/api/ruleName/delete/{id}", 8))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/ruleName/list"));

        assertAll(
                () -> assertThat(ruleNameTestH2Repository.findById(8)).isEmpty(),
                () -> assertThat(ruleNameTestH2Repository.findAll().size()).isEqualTo(0)
        );
    }
}
