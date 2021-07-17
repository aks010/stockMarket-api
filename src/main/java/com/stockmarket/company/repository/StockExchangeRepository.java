package com.stockmarket.company.repository;

import com.stockmarket.company.entity.StockExchange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockExchangeRepository extends JpaRepository<StockExchange, Long> {
    public Optional<StockExchange> findByName(String exchangeName
    );
}
