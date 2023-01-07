package com.nnk.springboot.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CurvePointDTO {
    private Integer curveid;
    @Min(1)
    @NotNull(message = "CurveId must not be null")
    private Integer curveId;
    @NotNull(message = "Term must not be null")
    private Double term;
    @NotNull(message = "Value must not be null")
    private Double value;

    public CurvePointDTO() {
    }

    public CurvePointDTO(Integer curveId, Double term, Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

    public Integer getCurveId() {
        return curveId;
    }

    public void setCurveId(Integer curveId) {
        this.curveId = curveId;
    }

    public Double getTerm() {
        return term;
    }

    public void setTerm(Double term) {
        this.term = term;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
    public Integer getCurveid() {
        return curveid;
    }
    public void setCurveid(Integer curveid) {
        this.curveid = curveid;
    }
}
