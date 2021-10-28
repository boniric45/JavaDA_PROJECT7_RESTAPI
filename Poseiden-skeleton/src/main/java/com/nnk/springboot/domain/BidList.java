package com.nnk.springboot.domain;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.security.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor // Generate all Arg Contructor
@NoArgsConstructor
@Table(name = "bidlist")
public class BidList {

    @Transient
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp date;

    @Id
    @Column(name = "BidListId", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer BidListId;
    @Column(name = "account")
    String account;
    @Column(name = "type")
    String type;
    @Column(name = "bidquantity")

    @Pattern(regexp = "[+-]?([0-9]*[.])?[0-9]+")
    Double bidQuantity;

    @Column(name = "askQuantity")
    Double askQuantity;
    @Column(name = "bid")
    Double bid;
    @Column(name = "ask")
    Double ask;
    @Column(name = "benchmark")
    String benchmark;
    @Transient
    @Column(name = "bidListDate")
    Timestamp bidListDate;
    @Column(name = "commentary")
    String commentary;
    @Column(name = "security")
    String security;
    @Column(name = "status")
    String status;
    @Column(name = "trader")
    String trader;
    @Column(name = "book")
    String book;
    @Column(name = "creationName")
    String creationName;
    @Transient
    @Column(name = "creationDate")
    Timestamp creationDate = date;
    @Column(name = "revisionName")
    String revisionName;
    @Transient
    @Column(name = "revisionDate")
    Timestamp revisionDate;
    @Column(name = "dealName")
    String dealName;
    @Column(name = "dealType")
    String dealType;
    @Column(name = "sourceListId")
    String sourceListId;
    @Column(name = "side")
    String side;


// TODO: Map columns in data table BIDLIST with corresponding java fields
}
