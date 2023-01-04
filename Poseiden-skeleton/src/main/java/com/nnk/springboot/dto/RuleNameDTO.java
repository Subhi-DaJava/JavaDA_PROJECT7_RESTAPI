package com.nnk.springboot.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RuleNameDTO {
    public Integer ruleNameId;

    @Size(max = 20)
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Description is mandatory")
    @Size(max = 30)
    private String description;
    @Size(max = 20)
    @NotBlank(message = "Json is mandatory")
    private String json;
    @Size(max = 20)
    @NotBlank(message = "Template is mandatory")
    private String template;
    @Size(max = 20)
    @NotBlank(message = "SqlStr is mandatory")
    private String sqlStr;
    @Size(max = 20)
    @NotBlank(message = "SqlPart is mandatory")
    private String sqlPart;

    public RuleNameDTO() {
    }

    public RuleNameDTO(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }

    public Integer getRuleNameId() {
        return ruleNameId;
    }

    public void setRuleNameId(Integer ruleNameId) {
        this.ruleNameId = ruleNameId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSqlStr() {
        return sqlStr;
    }

    public void setSqlStr(String sqlStr) {
        this.sqlStr = sqlStr;
    }

    public String getSqlPart() {
        return sqlPart;
    }

    public void setSqlPart(String sqlPart) {
        this.sqlPart = sqlPart;
    }
}
