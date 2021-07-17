package com.stockmarket.company.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="StockExchange")
@NamedQuery(name = "StockExchange.findByName", query = "SELECT se FROM StockExchange se WHERE se.exchangeName = :exchangeName")
public class StockExchange {
    @Id
    @GeneratedValue
    private long id;
    private String exchangeName;

    @OneToMany(targetEntity = CompanyStockExchangeMap.class, mappedBy = "stockExchange")
    @JsonIgnore
    private List<CompanyStockExchangeMap> compStockMap;

    @ManyToMany
    @JsonIgnore
    private List<IPODetail> ipoDetails = new ArrayList<>();


    // CONSTRUCTORS
    public StockExchange() {super();};

    public StockExchange(String exchangeName) {
        super();
        this.exchangeName = exchangeName;
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

    public List<CompanyStockExchangeMap> getCompStockMap() {
        return compStockMap;
    }

    public void setCompStockMap(List<CompanyStockExchangeMap> compStockMap) {
        this.compStockMap = compStockMap;
    }

    public void addCompStockMap(CompanyStockExchangeMap compStockMapElement) {
        this.compStockMap.add( compStockMapElement);
    }


    public List<IPODetail> getIpoDetails() {
        return ipoDetails;
    }

    public void setIpoDetails(List<IPODetail> ipoDetails) {
        this.ipoDetails = ipoDetails;
    }

    public void addIpoDetail(IPODetail ipoDetail) {
        this.ipoDetails.add(ipoDetail);
    }
}
