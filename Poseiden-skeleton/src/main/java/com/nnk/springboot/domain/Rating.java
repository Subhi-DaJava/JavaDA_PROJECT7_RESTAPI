package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "moddys_rating", length = 125)
    private String moddysRating;
    @Column(name = "sand_p_rating", length = 125)
    private String sandPRating;
    @Column(name = "fitch_rating", length = 125)
    private String fitchRating;

    @Column(name = "order_number") //Todo: size?
    private Integer orderNumber;

    public Rating() {
    }

    public Rating(Integer id, String moddysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.id = id;
        this.moddysRating = moddysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }

    public Rating(String moddysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moddysRating = moddysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModdysRating() {
        return moddysRating;
    }

    public void setModdysRating(String moddysRating) {
        this.moddysRating = moddysRating;
    }

    public String getSandPRating() {
        return sandPRating;
    }

    public void setSandPRating(String sandPRating) {
        this.sandPRating = sandPRating;
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

    public void setOrderNumber(Integer oderNumber) {
        this.orderNumber = orderNumber;
    }
}
