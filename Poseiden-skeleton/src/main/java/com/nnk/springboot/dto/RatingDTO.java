package com.nnk.springboot.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RatingDTO {
    private Integer ratingID;
    @Size(max = 20, message = "Max characters 20")
    private String moodysRating;
    @Size(max = 20, message = "Max characters 20")
    private String sandPRating;
    @Size(max = 20, message = "Max characters 20")
    private String fitchRating;
    @Min(1)
    @NotNull(message = "must not be null")
    private Integer orderNumber;

    public Integer getRatingID() {
        return ratingID;
    }
    public void setRatingID(Integer ratingID) {
        this.ratingID = ratingID;
    }
    public String getSandPRating() {
        return sandPRating;
    }

    public void setSandPRating(String sandPRating) {
        this.sandPRating = sandPRating;
    }

    public String getMoodysRating() {
        return moodysRating;
    }

    public void setMoodysRating(String moodysRating) {
        this.moodysRating = moodysRating;
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
