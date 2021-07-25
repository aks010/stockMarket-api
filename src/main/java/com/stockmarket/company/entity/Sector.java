package com.stockmarket.company.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "Sector.findByName",query = "SELECT s FROM Sector s WHERE s.sectorName = :sectorName")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Sector {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    @NotBlank(message = "{sectorName.required}")
    private String sectorName;
    private String brief;

    @OneToMany(mappedBy = "sector")
    @JsonIgnore
    private List<Company> companies = new ArrayList<>();


    // CONSTRUCTOR
    public Sector() {super();}

    public Sector(String sectorName, String brief) {
        super();
        this.sectorName = sectorName;
        this.brief=  brief;
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

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }
}
