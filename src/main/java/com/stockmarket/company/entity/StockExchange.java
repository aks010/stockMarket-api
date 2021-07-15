package com.stockmarket.company.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="StockExchange")
//@NamedQuery(name = "StockExchange.findByName", query = "SELECT se FROM StockExchange WHERE se.name = :name")
public class StockExchange {
    @Id
    @GeneratedValue
    private long id;
    private String exchangeName;

    @OneToMany(targetEntity = CompanyStockExchangeMap.class, mappedBy = "stockExchange")
    private List<CompanyStockExchangeMap> compStockMap;

    @OneToMany(targetEntity = IPODetailStockExchangeMap.class, mappedBy = "stockExchange")
    private List<IPODetailStockExchangeMap> ipoDetailStockExchangeMaps;

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

    public List<IPODetailStockExchangeMap> getIpoDetailStockExchangeMaps() {
        return ipoDetailStockExchangeMaps;
    }

    public void setIpoDetailStockExchangeMaps(List<IPODetailStockExchangeMap> ipoDetailStockExchangeMaps) {
        this.ipoDetailStockExchangeMaps = ipoDetailStockExchangeMaps;
    }
}
