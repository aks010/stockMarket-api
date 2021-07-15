package com.stockmarket.company.entity;


import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="StockPrice")
public class StockPrice {
    @Id
    @GeneratedValue
    private long id;
    private String exchangeName;
    private String companyCode;
    private LocalDateTime localDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;
    private Date datee;
    private Time timee;

    private float sharePrice;

    // CONSTRUCTORS

    public StockPrice() {};

    public StockPrice(String exchangeName, String companyCode, Date datee, float sharePrice) {
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

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Date getDatee() {
        return datee;
    }

    public void setDatee(Date datee) {
        this.datee = datee;
    }

    public Time getTimee() {
        return timee;
    }

    public void setTimee(Time timee) {
        this.timee = timee;
    }

    public float getSharePrice() {
        return sharePrice;
    }

    public void setSharePrice(float sharePrice) {
        this.sharePrice = sharePrice;
    }
}
