package com.stockmarket.company.service;

import com.stockmarket.company.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICompanyService {
    public Page<Company> listCompanies(Pageable pageable);
    public Company newCompany(Company company, String exchangeName, String sectorName);
    public Company getCompany(Long companyId);
    public Company updateCompany(Long companyId, Company company);
    public void removeCompany(Long stockId);
}
