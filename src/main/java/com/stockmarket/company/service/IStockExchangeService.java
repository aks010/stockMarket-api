package com.stockmarket.company.service;

import com.stockmarket.company.entity.Company;
import com.stockmarket.company.entity.StockExchange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IStockExchangeService {
    public Page<StockExchange> listStockExchanges(Pageable pageable);
    public StockExchange newStockExchange(StockExchange stockExchange);
    public StockExchange getStockExchange(String exchangeName);
    public StockExchange updateStockExchange(StockExchange stockExchange);
    public void removeStockExchange(String exchangeName);
    public List<Company> getCompanyList(String exchangeName);

}
