package com.stockmarket.company.service;

import com.stockmarket.company.entity.Company;
import com.stockmarket.company.entity.StockExchange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StockExchangeService {
    public List<StockExchange> listStockExchanges();
    public StockExchange newStockExchange(StockExchange stockExchange);
    public StockExchange getStockExchange(String exchangeName);
    public StockExchange updateStockExchange(String exchangeName, StockExchange stockExchange);
    public void removeStockExchange(String exchangeName);
    public List<Company> getCompanyList(String exchangeName);

}
