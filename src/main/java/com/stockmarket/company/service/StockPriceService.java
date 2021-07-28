package com.stockmarket.company.service;

import com.stockmarket.company.entity.Company;
import com.stockmarket.company.entity.CompareConfig;
import com.stockmarket.company.entity.StockPrice;

import java.util.HashMap;
import java.util.List;

public interface StockPriceService {
    public List<StockPrice> listStockPrices();
    public List<Object> compareCompanies(CompareConfig companyList);
    public HashMap<String, Object> uploadExcel(List<StockPrice> stockPriceList);
    public StockPrice getStockPrice(Long stockId);
    public void removeStockPrice(Long stockId);
}
