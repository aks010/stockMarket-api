package com.stockmarket.company.repository;

import com.stockmarket.company.entity.Company;
import com.stockmarket.company.entity.CompanyStockExchangeMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompanyStockExchangeMapRepository extends JpaRepository<CompanyStockExchangeMap, Long> {
}
