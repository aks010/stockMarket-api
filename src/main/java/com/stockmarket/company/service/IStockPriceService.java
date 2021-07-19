package com.stockmarket.company.service;

import com.stockmarket.company.entity.Company;
import com.stockmarket.company.entity.StockPrice;

import java.util.List;

public interface IStockPriceService {
    public List<StockPrice> listStockPrices();
    public List<Company> listStockPriceCompanies( String sectorName);
    public StockPrice newStockPrice(StockPrice company, String companyName);
    public void uploadExcel(List<StockPrice> stockPriceList);
    public StockPrice getStockPrice(Long sectorId);
    public StockPrice getStockPriceByName(String sectorName);
    public StockPrice updateStockPrice(Long sectorId, StockPrice sector);
    public void removeStockPrice(Long stockId);
}
