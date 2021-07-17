package com.stockmarket.company.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Company")
@NamedQuery(name = "Company.findByName", query = "SELECT c FROM Company c WHERE c.companyName = :companyName")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Company {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private Double turnover;

    @Column(nullable = false)
    private String ceo;

    @Column(nullable = false)
    @Type(type = "text")
    private String boardOfDirectors;

    @Column(nullable = false)
    @Type(type = "text")
    private String companyBrief;

    @OneToMany(targetEntity = CompanyStockExchangeMap.class, mappedBy = "company")
    private List<CompanyStockExchangeMap> compStockMap = new ArrayList<>();

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    private List<StockPrice> stockPrices = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Sector sector;

    @OneToOne(mappedBy = "company")
    @JsonIgnore
    private IPODetail ipoDetail;

    // CONSTRUCTORS
    public Company() {
        super();
    };

    public Company (Company company) {
        this.setCompanyName(company.getCompanyName());
        this.setIpoDetail(company.getIpoDetail());
        this.setCompanyBrief(company.getCompanyBrief());
        this.setCeo(company.getCeo());
        this.setSector(company.getSector());
        this.setBoardOfDirectors(company.getBoardOfDirectors());
        this.setTurnover(company.getTurnover());
    }



    public Company(String companyName, Double turnover, String ceo, String boardOfDirectors, String companyBrief) {
        super();
        this.companyName = companyName;
        this.turnover = turnover;
        this.ceo = ceo;
        this.boardOfDirectors = boardOfDirectors;
        this.companyBrief = companyBrief;
    }

    // GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getTurnover() {
        return turnover;
    }

    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }

    public String getCeo() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    public String getBoardOfDirectors() {
        return boardOfDirectors;
    }

    public void setBoardOfDirectors(String boardOfDirectors) {
        this.boardOfDirectors = boardOfDirectors;
    }

    public String getCompanyBrief() {
        return companyBrief;
    }

    public void setCompanyBrief(String companyBrief) {
        this.companyBrief = companyBrief;
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

    public List<StockPrice> getStockPrices() {
        return stockPrices;
    }

    public void addStockPrices(StockPrice stockPrice) {
        this.stockPrices.add(stockPrice);
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public IPODetail getIpoDetail() {
        return ipoDetail;
    }

    public void setIpoDetail(IPODetail ipoDetail) {
        this.ipoDetail = ipoDetail;
    }

}
