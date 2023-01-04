package com.nnk.springboot.service.rule_name_service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDTO;

import java.util.List;

public interface RuleNameService {
    List<RuleNameDTO> getRuleNames();
    RuleNameDTO getRuleNameById(Integer id);
    RuleNameDTO saveNewRuleName(RuleNameDTO ruleNameDTO);
    void updateRuleName(RuleNameDTO ruleNameDTO);
    void deleteRuleNameById(Integer id);
}
