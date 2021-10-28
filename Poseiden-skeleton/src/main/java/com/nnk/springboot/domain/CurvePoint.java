package com.nnk.springboot.domain;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;


@Getter
@Setter
@Entity
@Table(name = "curvepoint")

public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    Integer id;
    @Column(name = "CurveId")
    Integer curveId;
    @Column(name = "term")
    @Pattern(regexp = "[+-]?([0-9]*[.])?[0-9]+")
    Double term;
    @Column(name = "value")
    @Pattern(regexp = "[+-]?([0-9]*[.])?[0-9]+")
    Double value;

    @Transient
    @Column(name = "asOfDate")
    private Timestamp asOfDate;

    @Transient
    @Column(name = "creationDate")
    private Timestamp creationDate;

    public Timestamp getAsOfDate() { return (Timestamp) asOfDate.clone(); }

    public void setAsOfDate(Timestamp asOfDate) {
        this.asOfDate = (Timestamp) asOfDate.clone();
    }

    public Timestamp getCreationDate() {
        return (Timestamp) creationDate.clone();
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = (Timestamp) creationDate.clone();
    }
}


// TODO: Map columns in data table CURVEPOINT with corresponding java fields

