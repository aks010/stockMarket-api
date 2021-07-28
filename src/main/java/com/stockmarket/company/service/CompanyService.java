package com.stockmarket.company.service;

import com.stockmarket.company.entity.Company;
import com.stockmarket.company.entity.CompanyStockExchangeMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {
    public List<Company> listCompanies();
    public List<Company> listCompaniesByPattern(String startsWith);
    public Company newCompany(Company company, String sectorName);
    public Company getCompanyByName(String companyName);
    public CompanyStockExchangeMap mapCompanyExchange(String companyName, String exchangeName, CompanyStockExchangeMap compSeMap);
    public Company updateCompany(String companyName, Company company);
    public void removeCompany(String companyName);

}
