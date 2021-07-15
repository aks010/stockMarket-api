package com.stockmarket.company.repository;

import com.stockmarket.company.entity.StockExchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockExchangeRepository extends JpaRepository<StockExchange, Long> {
//    StockExchange findByName(String name);
}
