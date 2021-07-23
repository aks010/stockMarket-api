package com.stockmarket.company.repository;

import com.stockmarket.company.entity.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {
//    public List<StockPrice> findByOpenInBetween(LocalDate from, LocalDate to);
    public List<Object> findByDatee(LocalDate from, LocalDate to, String companyName);
}
