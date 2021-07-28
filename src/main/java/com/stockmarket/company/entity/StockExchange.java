package com.stockmarket.company.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="StockExchange")
@NamedQuery(name = "StockExchange.findByName", query = "SELECT se FROM StockExchange se WHERE se.exchangeName = :exchangeName")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StockExchange {
    @Id
    @GeneratedValue
    private long id;
    private String exchangeName;
    private String brief;
    private String contactAddress;
    private String remarks;

    @OneToMany(targetEntity = CompanyStockExchangeMap.class, mappedBy = "stockExchange")
    @JsonIgnore
    private List<CompanyStockExchangeMap> compStockMap;

    @ManyToMany
    @JsonIgnore
    private List<IPODetail> ipoDetails = new ArrayList<>();


    // CONSTRUCTORS
    public StockExchange() {super();};

    public StockExchange(String exchangeName, String brief, String contactAddress, String remarks) {
        super();
        this.exchangeName = exchangeName;
        this.brief = brief;
        this.contactAddress = contactAddress;
        this.remarks = remarks;
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

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
