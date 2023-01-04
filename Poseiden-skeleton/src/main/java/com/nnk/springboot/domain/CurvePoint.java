package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Min(1)
    @Column(name = "curveid")
    @NotNull(message = "must not be null")
    private Integer curveId;

    @Column(name = "asofdate")
    private Timestamp asOfDate;
    @NotNull
    private Double term;
    @NotNull
    private Double value;

    @Column(name = "creationdate")
    private Timestamp creationDate;

    public CurvePoint() {
    }

    public CurvePoint(Integer id, Integer curveId, Timestamp asOfDate, Double term, Double value, Timestamp creationDate) {
        this.id = id;
        this.curveId = curveId;
        this.asOfDate = asOfDate;
        this.term = term;
        this.value = value;
        this.creationDate = creationDate;
    }

    public CurvePoint(Integer curveId, Double term, Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }
    public CurvePoint(Integer id, Integer curveId, Double term, Double value) {
        this.id = id;
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCurveId() {
        return curveId;
    }

    public void setCurveId(Integer curveId) {
        this.curveId = curveId;
    }

    public Timestamp getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate(Timestamp asOfDate) {
        this.asOfDate = asOfDate;
    }

    public Double getTerm() {
        return term;
    }

    public void setTerm(Double term) {
        this.term = term;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
