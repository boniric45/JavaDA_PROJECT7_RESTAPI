package com.nnk.springboot.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "rulename")
@NoArgsConstructor
@Builder
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    Integer id;
    @Column(name = "name")
    String name;
    @Column(name = "description")
    String description;
    @Column(name = "json")
    String json;
    @Column(name = "template")
    String template;
    @Column(name = "sqlStr")
    String sqlStr;
    @Column(name = "sqlPart")
    String sqlPart;

    public RuleName(Integer id, String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }
}
