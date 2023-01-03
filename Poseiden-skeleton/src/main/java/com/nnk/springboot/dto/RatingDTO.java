package com.nnk.springboot.dto;

public class RatingDTO {
    private String moddysRating;
    private String sandPRating;
    private String fitchRating;
    private Integer orderNumber;

    public String getSandPRating() {
        return sandPRating;
    }

    public void setSandPRating(String sandPRating) {
        this.sandPRating = sandPRating;
    }

    public String getModdysRating() {
        return moddysRating;
    }

    public void setModdysRating(String moddysRating) {
        this.moddysRating = moddysRating;
    }

    public String getFitchRating() {
        return fitchRating;
    }

    public void setFitchRating(String fitchRating) {
        this.fitchRating = fitchRating;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}
