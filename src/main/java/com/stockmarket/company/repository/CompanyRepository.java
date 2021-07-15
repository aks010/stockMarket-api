package com.stockmarket.company.repository;

import com.stockmarket.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompanyRepository extends JpaRepository<Company, Long>, PagingAndSortingRepository<Company, Long> {
//    Company findByName(String companyName);
}
