package com.stockmarket.company.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "CompanyStockExchangeMap")
@NamedQuery(name = "CompanyStockExchangeMap.findByName", query = "SELECT c FROM CompanyStockExchangeMap c WHERE c.companyCode = :companyCode")
public class CompanyStockExchangeMap {
    @Id
    @GeneratedValue
    private long id;

    // DOUBT !!
    private String companyCode;

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

    public StockExchange getStockExchange() {
        return stockExchange;
    }

    public void setStockExchange(StockExchange stockExchange) {
        this.stockExchange = stockExchange;
    }
}
