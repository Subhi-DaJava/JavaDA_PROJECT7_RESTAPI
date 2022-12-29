package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table(name = "trade")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tradeId")
    public Integer tradeId;
    @NotNull(message = "Accont is mandatory")
    @Column(length = 30)
    private String account;
    @NotNull(message = "Type is mandatory")
    @Column(length = 30)
    public String type;

    @Column(name = "buyQuantity")
    private Double buyQuantity;
    @Column(name = "selQuantity")
    private Double selQuantity;
    @Column(name = "buyPrice")
    private Double buyPrice;
    @Column(name = "selPrice")
    private Double selPrice ;

    @Column(name = "tradeDate")
    private Timestamp tradeDate;

    @Column(length = 125)
    private String security;
    @Column(length = 10)
    private String status;
    @Column(length = 125)
    private String trader;
    @Column(length = 125)
    private String benchmark;
    @Column(length = 125)
    private String book;

    @Column(length = 125, name = "creationName")
    private String creationName;
    @Column(name = "creationDate")
    private Timestamp creationDate;
    @Column(name = "revisionName", length = 125)
    private String revisionName;
    @Column(name = "revisionDate")
    private Timestamp revisionDate;
    @Column(name = "dealName", length = 125)
    private String dealName;
    @Column(name = "dealType", length = 125)
    private String dealType;
    @Column(name = "sourceListId", length = 125)
    private String sourceListId;

    private String side;

    public Trade() {
    }

    public Trade(Integer tradeId,
                 String account, String type,
                 Double buyQuantity, Double selQuantity, Double buyPrice,
                 Double selPrice, Timestamp tradeDate, String security,
                 String status, String trader, String benchmark, String book,
                 String creationName, Timestamp creationDate, String revisionName, Timestamp revisionDate,
                 String dealName, String dealType, String sourceListId, String side) {
        this.tradeId = tradeId;
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
        this.selQuantity = selQuantity;
        this.buyPrice = buyPrice;
        this.selPrice = selPrice;
        this.tradeDate = tradeDate;
        this.security = security;
        this.status = status;
        this.trader = trader;
        this.benchmark = benchmark;
        this.book = book;
        this.creationName = creationName;
        this.creationDate = creationDate;
        this.revisionName = revisionName;
        this.revisionDate = revisionDate;
        this.dealName = dealName;
        this.dealType = dealType;
        this.sourceListId = sourceListId;
        this.side = side;
    }

    public Trade(String account, String type) {
        this.account = account;
        this.type = type;
    }

    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

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

    public Double getSelQuantity() {
        return selQuantity;
    }

    public void setSelQuantity(Double selQuantity) {
        this.selQuantity = selQuantity;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Double getSelPrice() {
        return selPrice;
    }

    public void setSelPrice(double selPrice) {
        this.selPrice = selPrice;
    }

    public Timestamp getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Timestamp tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public String getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getCreationName() {
        return creationName;
    }

    public void setCreationName(String creationName) {
        this.creationName = creationName;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getRevisionName() {
        return revisionName;
    }

    public void setRevisionName(String revisionName) {
        this.revisionName = revisionName;
    }

    public Timestamp getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Timestamp revisionDate) {
        this.revisionDate = revisionDate;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getSourceListId() {
        return sourceListId;
    }

    public void setSourceListId(String sourceListId) {
        this.sourceListId = sourceListId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
