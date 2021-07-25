package com.stockmarket.company.repository;

import com.stockmarket.company.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    public Optional<Company> findByName(String companyName);
    public List<Company> findByNameStartWith(String startsWith);
}
