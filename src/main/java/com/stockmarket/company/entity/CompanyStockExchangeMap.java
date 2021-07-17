package com.stockmarket.company.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "CompanyStockExchangeMap")
public class CompanyStockExchangeMap {
    @Id
    @GeneratedValue
    private long id;

    // DOUBT !!
    private String CompanyCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    private StockExchange stockExchange;

    // CONSTRUCTOR

    public CompanyStockExchangeMap () {
        super();
    }

    // GETTERS AND SETTERS

    public String getCompanyCode() {
        return CompanyCode;
    }

    public void setCompanyCode(String companyCode) {
        CompanyCode = companyCode;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public StockExchange getStockExchange() {
        return stockExchange;
    }

    public void setStockExchange(StockExchange stockExchange) {
        this.stockExchange = stockExchange;
    }
}
