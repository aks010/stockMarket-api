package com.stockmarket.company.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name="StockPrice")
//@NamedQuery(name = "StockPrice.findByDatee", query = "SELECT c FROM StockPrice c WHERE c.datee BETWEEN :from AND :to ")
@NamedQuery(name = "StockPrice.findByDatee", query = "SELECT sp.datee,c.companyName,  SUM(sp.sharePrice) FROM StockPrice sp JOIN sp.company c WHERE sp.datee BETWEEN :from AND :to AND c.companyName= :companyName GROUP BY sp.datee")
public class StockPrice {
    @Id
    @GeneratedValue
    private long id;
    private String exchangeName;
    private String companyCode;
//    private LocalDateTime localDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Company company;
    private LocalDate datee;
    private LocalTime timee;

    private float sharePrice;

    // CONSTRUCTORS

    public StockPrice() {};

    public StockPrice(String exchangeName, String companyCode, LocalDate datee, float sharePrice) {
        super();
        this.exchangeName = exchangeName;
        this.companyCode = companyCode;
        this.datee = datee;
        //TODO
//        this.timee = timee;
        this.sharePrice = sharePrice;
    }

    // GETTERS AND SETTERS


    public long getId() {
        return id;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public LocalDate getDatee() {
        return datee;
    }

    public void setDatee(LocalDate datee) {
        this.datee = datee;
    }

    public LocalTime getTimee() {
        return timee;
    }

    public void setTimee(LocalTime timee) {
        this.timee = timee;
    }

    public float getSharePrice() {
        return sharePrice;
    }

    public void setSharePrice(float sharePrice) {
        this.sharePrice = sharePrice;
    }
}
