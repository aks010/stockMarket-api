package com.stockmarket.company.entity;

import org.springframework.jmx.export.annotation.ManagedAttribute;

import javax.persistence.*;

@Entity
public class IPODetailStockExchangeMap {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private IPODetail ipoDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    private StockExchange stockExchange;

    // CONSTRUCTOR
    public IPODetailStockExchangeMap() {super();}

    // GETTERS AND SETTERS


    public IPODetail getIpoDetail() {
        return ipoDetail;
    }

    public void setIpoDetail(IPODetail ipoDetail) {
        this.ipoDetail = ipoDetail;
    }

    public StockExchange getStockExchange() {
        return stockExchange;
    }

    public void setStockExchange(StockExchange stockExchange) {
        this.stockExchange = stockExchange;
    }
}
