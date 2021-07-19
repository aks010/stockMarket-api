package com.stockmarket.company.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "Sector.findByName",query = "SELECT s FROM Sector s WHERE s.sectorName = :sectorName")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Sector {
    @Id
    @GeneratedValue
    private long id;
    private String sectorName;

    @OneToMany(mappedBy = "sector")
    @JsonIgnore
    private List<Company> companies = new ArrayList<>();


    // CONSTRUCTOR
    public Sector() {super();}

    public Sector(String sectorName) {
        super();
        this.sectorName = sectorName;
    }

    // GETTERS AND SETTERS

    public long getId() {
        return id;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void addCompany(Company company) {
        this.companies.add(company);
    }
}