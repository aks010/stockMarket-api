package com.stockmarket.company.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class IPODetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Double pricePerShare;

    @Column(nullable = false)
    private Long totalNumberOfShares;

    private LocalDateTime openDateTime;

    private String remarks;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Company company;

    @ManyToMany
    private List<StockExchange> stockExchanges = new ArrayList<>();

    // CONSTRUCTORS
    protected IPODetail() {
    }

    public IPODetail(double pricePerShare, Long totalNumberOfShares, LocalDateTime openDateTime) {
        super();
        this.pricePerShare = pricePerShare;
        this.totalNumberOfShares = totalNumberOfShares;
        this.openDateTime = openDateTime;
    }

    public IPODetail(Double pricePerShare, Long totalNumberOfShares, LocalDateTime openDateTime, String remarks) {
        this.pricePerShare = pricePerShare;
        this.totalNumberOfShares = totalNumberOfShares;
        this.openDateTime = openDateTime;
        this.remarks = remarks;
    }

    // GETTERS AND SETTERS


    public Long getId() {
        return id;
    }

    public Double getPricePerShare() {
        return pricePerShare;
    }

    public void setPricePerShare(Double pricePerShare) {
        this.pricePerShare = pricePerShare;
    }

    public Long getTotalNumberOfShares() {
        return totalNumberOfShares;
    }

    public void setTotalNumberOfShares(Long totalNumberOfShares) {
        this.totalNumberOfShares = totalNumberOfShares;
    }

    public LocalDateTime getOpenDateTime() {
        return openDateTime;
    }

    public void setOpenDateTime(LocalDateTime openDateTime) {
        this.openDateTime = openDateTime;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<StockExchange> getStockExchanges() {
        return stockExchanges;
    }

    public void setStockExchanges(List<StockExchange> stockExchanges) {
        this.stockExchanges = stockExchanges;
    }

    public void addStockExchange(StockExchange stockExchange) {
        this.stockExchanges.add(stockExchange);
    }
}
