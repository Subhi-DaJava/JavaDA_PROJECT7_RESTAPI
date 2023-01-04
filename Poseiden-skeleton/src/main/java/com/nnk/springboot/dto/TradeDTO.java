package com.nnk.springboot.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TradeDTO {
    private Integer id;
    @Size(max = 20)
    @NotBlank(message = "Account is mandatory")
    private String account;
    @Size(max = 20)
    @NotBlank(message = "Type is mandatory")
    public String type;
    @Min(1)
    @NotNull(message = "Should not be null")
    private Double buyQuantity;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(Double buyQuantity) {
        this.buyQuantity = buyQuantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
