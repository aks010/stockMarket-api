package com.stockmarket.company.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CompareConfig {
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate from;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate to;
    public List<CompanyExchange> companyList = new ArrayList<>();
    public List<String> sectorList = new ArrayList<>();
}

