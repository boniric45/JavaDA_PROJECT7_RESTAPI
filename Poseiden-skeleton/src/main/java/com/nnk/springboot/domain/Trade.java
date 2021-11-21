package com.nnk.springboot.domain;

import jakarta.validation.constraints.Pattern;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "trade")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TradeId", nullable = false)
    Integer tradeId;
    @Column(name = "account")
    String account;
    @Column(name = "type")
    String type;
    @Column(name = "buyQuantity")
    @Pattern(regexp = "[+-]?([0-9]*[.])?[0-9]+")
    Double buyQuantity;
    @Column(name = "sellQuantity")
    @Pattern(regexp = "[+-]?([0-9]*[.])?[0-9]+")
    Double sellQuantity;
    @Column(name = "buyPrice")
    @Pattern(regexp = "[+-]?([0-9]*[.])?[0-9]+")
    Double buyPrice;
    @Column(name = "sellPrice")
    @Pattern(regexp = "[+-]?([0-9]*[.])?[0-9]+")
    Double sellPrice;
    @Column(name = "security")
    String security;
    @Column(name = "status")
    String status;
    @Column(name = "trader")
    String trader;
    @Column(name = "benchmark")
    String benchmark;
    @Column(name = "book")
    String book;
    @Column(name = "creationName")
    String creationName;
    @Column(name = "revisionName")
    String revisionName;
    @Column(name = "dealName")
    String dealName;
    @Column(name = "dealType")
    String dealType;
    @Column(name = "sourceListId")
    String sourceListId;
    @Column(name = "side")
    String side;
    @Transient
    @Column(name = "tradeDate")
    private Timestamp tradeDate;
    @Transient
    @Column(name = "creationDate")
    private Timestamp creationDate;
    @Transient
    @Column(name = "revisionDate")
    private Timestamp revisionDate;

    public Trade(Integer tradeId, String account, String type, Double buyQuantity) {
        this.tradeId = tradeId;
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }

    // fix Bugs
    public Timestamp getTradeDate() {
        return (Timestamp) tradeDate.clone();
    }

    public void setTradeDate(Timestamp tradeDate) {
        this.tradeDate = (Timestamp) tradeDate.clone();
    }

    public Timestamp getCreationDate() {
        return (Timestamp) creationDate.clone();
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = (Timestamp) creationDate.clone();
    }

    public Timestamp getRevisionDate() {
        return (Timestamp) revisionDate.clone();
    }

    public void setRevisionDate(Timestamp revisionDate) {
        this.revisionDate = (Timestamp) revisionDate.clone();
    }

}
