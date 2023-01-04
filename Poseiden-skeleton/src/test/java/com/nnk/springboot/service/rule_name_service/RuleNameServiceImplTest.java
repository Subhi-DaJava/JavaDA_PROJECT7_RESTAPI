package com.nnk.springboot.service.rule_name_service;


import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.mapper.MapperService;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RuleNameServiceImplTest {
    @InjectMocks
    private RuleNameServiceImpl ruleNameService;
    @Mock
    private RuleNameRepository ruleNameRepository;
    @Mock
    private MapperService mapperService;

    @Test
    void getRuleNames() {
        RuleNameDTO ruleNameDTO1 = new RuleNameDTO();
        RuleNameDTO ruleNameDTO2 = new RuleNameDTO();


        RuleName ruleName1 = new RuleName("name1", "description1", "json1", "template1", "sqlStr1", "sqlPart1");
        RuleName ruleName2 = new RuleName("name2", "description2", "json2", "template2", "sqlStr2", "sqlPart2");

        List<RuleName> ruleNames = new ArrayList<>(List.of(ruleName1, ruleName2));

        when(mapperService.fromRuleName(ruleName1)).thenReturn(ruleNameDTO1);
        when(mapperService.fromRuleName(ruleName2)).thenReturn(ruleNameDTO2);

        ruleNameDTO1.setName(ruleName1.getName());
        ruleNameDTO1.setDescription(ruleName1.getDescription());
        ruleNameDTO1.setJson(ruleName1.getJson());
        ruleNameDTO1.setTemplate(ruleName1.getTemplate());
        ruleNameDTO1.setSqlStr(ruleName1.getSqlStr());
        ruleNameDTO1.setSqlPart(ruleName1.getSqlPart());

        when(ruleNameRepository.findAll()).thenReturn(ruleNames);

        List<RuleNameDTO> ruleNameDTOList = ruleNameService.getRuleNames();

        assertThat(ruleNameDTOList.size()).isEqualTo(2);
        assertThat(ruleNameDTOList.get(0).getJson()).isEqualTo("json1");
        assertThat(ruleNameDTOList).isNotNull();
    }

    @Test
    void getRuleNameById() {
        RuleNameDTO ruleNameDTO = new RuleNameDTO();
        RuleName ruleName = new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart");
        ruleName.setId(5);

        //this mock will map any RuleName to RuleNameDTO
        when(mapperService.fromRuleName(any())).thenReturn(ruleNameDTO);
        ruleNameDTO.setRuleNameId(ruleName.getId());
        ruleNameDTO.setJson(ruleName.getJson());

        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.of(ruleName));

        RuleNameDTO ruleNameDTOGetById = ruleNameService.getRuleNameById(ruleName.getId());

        assertThat(ruleNameDTOGetById.getRuleNameId()).isEqualTo(ruleName.getId());
        assertThat(ruleNameDTOGetById.getJson()).isEqualTo(ruleName.getJson());
    }

    @Test
    void saveNewRuleName() {
        RuleNameDTO ruleNameDTO= new RuleNameDTO();
        RuleName ruleName = new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart");
        //this mock will map any RuleName to RuleNameDTO
        when(mapperService.fromRuleName(any())).thenReturn(ruleNameDTO);

        ruleNameDTO.setName(ruleName.getName());
        ruleNameDTO.setDescription(ruleName.getDescription());
        ruleNameDTO.setTemplate(ruleName.getTemplate());
        ruleNameDTO.setJson(ruleName.getJson());
        ruleNameDTO.setSqlStr(ruleName.getSqlStr());
        ruleNameDTO.setSqlPart(ruleName.getSqlPart());

        when(mapperService.fromRuleNameDTO(any())).thenReturn(ruleName);
        when(ruleNameRepository.save(any())).thenReturn(ruleName);

        RuleNameDTO ruleNameSaved = ruleNameService.saveNewRuleName(ruleNameDTO);

        assertThat(ruleNameSaved.getName()).isEqualTo(ruleName.getName());
        verify(ruleNameRepository, times(1)).save(any());
        verify(mapperService, times(1)).fromRuleName(any());
        verify(mapperService, times(1)).fromRuleNameDTO(any());

    }

    @Test
    void updateRuleName() {
        RuleName ruleName = new RuleName();
        RuleNameDTO ruleNameDTO= new RuleNameDTO();

        when(mapperService.fromRuleNameDTO(any())).thenReturn(ruleName);
        ruleName = new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart");
        ruleName.setId(36);

        when(ruleNameRepository.save(any())).thenReturn(ruleName);

        when(mapperService.fromRuleNameDTO(any())).thenReturn(ruleName);
        ruleName.setJson("json_coucou");
        ruleName.setName("coucou");
        ruleName.setId(35);
        ruleNameDTO.setName("coucou");
        ruleNameDTO.setJson("json_coucou");
        ruleNameDTO.setRuleNameId(35);

        ruleNameService.updateRuleName(ruleNameDTO);

        assertThat(ruleName.getName()).isEqualTo(ruleNameDTO.getName());
        assertThat(ruleName.getJson()).isEqualTo(ruleNameDTO.getJson());
        assertThat(ruleName.getId()).isEqualTo(35);
    }

    @Test
    void deleteRuleNameById() {
        RuleName ruleName = new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart");
        ruleName.setId(1);
        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.of(ruleName));
        doNothing().when(ruleNameRepository).deleteById(anyInt());

        ruleNameService.deleteRuleNameById(anyInt());

        verify(ruleNameRepository,times(1)).deleteById(anyInt());
        verify(ruleNameRepository, times(1)).findById(anyInt());
        assertThat(ruleNameService.getRuleNameById(ruleName.getId())).isNull();
    }
}