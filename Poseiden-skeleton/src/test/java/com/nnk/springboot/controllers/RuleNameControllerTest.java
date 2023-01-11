package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.service.rule_name_service.RuleNameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RuleNameController.class)
//@ExtendWith(SpringExtension.class)
class RuleNameControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private RuleNameService ruleNameService;

    private List<RuleNameDTO> ruleNameList;
    @BeforeEach
    void init() {
        RuleNameDTO ruleNameDTO =
                new RuleNameDTO("RuleName","description","json","template","sqlStr", "sqlPart");
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        ruleNameList = new ArrayList<>(List.of(ruleNameDTO));
    }
    @Test
    @WithMockUser
    void home() throws Exception {
        when(ruleNameService.getRuleNames()).thenReturn(ruleNameList);

        mockMvc.perform(get("/api/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("ruleNameList", ruleNameList))
                .andExpect(view().name("ruleName/list"));

    }

    @Test
    @WithMockUser
    void addRuleForm() throws Exception {
        mockMvc.perform(get("/api/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithMockUser
    void validate() throws Exception {
        RuleNameDTO ruleNameDTO =
                new RuleNameDTO("NewRuleName","NewDescription","Newjson","NewTemplate","NewSqlStr", "NewSqlPart");

        when(ruleNameService.saveNewRuleName(ruleNameDTO)).thenReturn(ruleNameDTO);

        mockMvc.perform(post("/api/ruleName/validate")
                .param("name", "NewRuleName")
                .param("description", "NewDescription")
                .param("json", "NewJson")
                .param("template", "NewTemplate")
                .param("sqlStr", "NewSqlStr")
                .param("sqlPart", "NewSqlPart")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/ruleName/list"));
    }

    @Test
    @WithMockUser
    void validateFailed() throws Exception {
        mockMvc.perform(post("/api/ruleName/validate"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithMockUser
    void showUpdateForm() throws Exception {
        RuleNameDTO ruleNameDTO =
                new RuleNameDTO("UpdateRuleName","UpdateDescription","UpdateJson","UpdateTemplate","UpdateSqlStr", "UpdateSqlPart");
        ruleNameDTO.setRuleNameId(10);
        when(ruleNameService.getRuleNameById(anyInt())).thenReturn(ruleNameDTO);

      mockMvc.perform(get("/api/ruleName/update/{id}", 10))
              .andExpect(model().attribute("ruleNameDTO", ruleNameDTO))
              .andExpect(status().isOk())
              .andExpect(view().name("ruleName/update"));
    }

    @Test
    @WithMockUser
    void updateRuleName() throws Exception {
        RuleNameDTO ruleNameDTO =
                new RuleNameDTO("UpdateRuleName","UpdateDescription","UpdateJson","UpdateTemplate","UpdateSqlStr", "UpdateSqlPart");
        ruleNameDTO.setRuleNameId(10);

        doNothing().when(ruleNameService).updateRuleName(ruleNameDTO);

        mockMvc.perform(post("/api/ruleName/update/{id}", 10)
                .param("name", "UpdateRuleName")
                .param("description", "UpdateDescription")
                .param("json", "UpdateJson")
                .param("template", "UpdateTemplate")
                .param("sqlStr", "UpdateSqlStr")
                .param("sqlPart", "UpdateSqlPart")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/ruleName/list"));
    }

    @Test
    @WithMockUser
    void updateRuleNameFailed() throws Exception {
        mockMvc.perform(post("/api/ruleName/update/{id}", 6))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    @WithMockUser
    void deleteRuleName() throws Exception {

       doNothing().when(ruleNameService).deleteRuleNameById(2);

       mockMvc.perform(get("/api/ruleName/delete/{id}", 2))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/api/ruleName/list"));
    }
}