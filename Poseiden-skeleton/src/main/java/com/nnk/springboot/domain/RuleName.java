package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Table(name = "rulename")
public class RuleName {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @NotBlank(message = "Name is mandatory")
    @Column(name = "name", length = 125)
    private String name;

    @NotBlank(message = "Description is mandatory")
    @Column(length = 30)
    private String description;

    @NotBlank(message = "Json is mandatory")
    @Column(length = 20)
    private String json;

    @NotBlank(message = "Template is mandatory")
    @Column(length = 20)
    private String template;

    @NotBlank(message = "SqlStr is mandatory")
    @Column(length = 20)
    private String sqlStr;

    @NotBlank(message = "SqlPart is mandatory")
    @Column(length = 20)
    private String sqlPart;

    public RuleName() {
    }

    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
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
