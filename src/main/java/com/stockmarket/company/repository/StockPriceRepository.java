package com.stockmarket.company.repository;

import com.stockmarket.company.entity.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {
}
