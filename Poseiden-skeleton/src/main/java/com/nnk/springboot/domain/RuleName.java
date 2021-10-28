package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "rulename")
@NoArgsConstructor
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

// TODO: Map columns in data table RULENAME with corresponding java fields
}
