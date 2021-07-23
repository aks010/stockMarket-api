package com.stockmarket.company.service;

import com.stockmarket.company.entity.Company;
import com.stockmarket.company.entity.CompanyStockExchangeMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICompanyService {
    public List<Company> listCompanies();
    public Company newCompanyWithSE(Company company, String exchangeName, String sectorName);
    public Company newCompany(Company company, String sectorName);
    public boolean isCompanyNameAvailable(String companyName);
    public Company getCompany(Long companyId);
    public Company getCompanyByName(String companyName);
    public CompanyStockExchangeMap mapCompanyExchange(String companyName, String exchangeName, CompanyStockExchangeMap compSeMap);
    public Company updateCompany(String companyName, Company company);
    public void removeCompany(Long stockId);

}
