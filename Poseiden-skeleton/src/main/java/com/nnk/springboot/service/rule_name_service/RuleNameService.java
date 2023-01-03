package com.nnk.springboot.service.rule_name_service;

import com.nnk.springboot.domain.RuleName;

import java.util.List;

public interface RuleNameService {
    List<RuleName> getRuleNames();
    RuleName getRuleNameById(Integer id);
    RuleName saveNewRuleName(RuleName ruleName);
    void updateRuleName(RuleName ruleName);
    void deleteRuleNameById(Integer id);
}
