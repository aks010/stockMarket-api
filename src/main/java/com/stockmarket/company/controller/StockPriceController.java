package com.stockmarket.company.controller;

import com.stockmarket.company.entity.Company;
import com.stockmarket.company.entity.CompareConfig;
import com.stockmarket.company.entity.StockPrice;
import com.stockmarket.company.exceptions.BadRequestException;
import com.stockmarket.company.service.StockPriceService;
import com.stockmarket.company.service.StockPriceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
public class StockPriceController {

    @Autowired
    private StockPriceService stockPriceService;

    @GetMapping("/stockPrices/list")
    public List<StockPrice> listStockPrices() {
        return stockPriceService.listStockPrices();
    }

    @PostMapping("/stockPrices/compare")
    public List<Object> compareCompanies(@RequestBody CompareConfig compare) {
        return stockPriceService.compareCompanies(compare);
    }

    // upload Stock Price Excel
    @PostMapping("/stockPrices/uploadExcel")
    public ResponseEntity<HashMap<String, Object>> newStockPrice(@Valid @RequestBody List<StockPrice> stockPriceList, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new BadRequestException("Please submit with valid entries!");
        }
        System.out.println(bindingResult);
        HashMap<String, Object> response = stockPriceService.uploadExcel(stockPriceList);
        return new ResponseEntity< HashMap<String, Object>>(response, null, HttpStatus.CREATED);
    }

    // Get a stockPrice by {stockPriceId}
    @GetMapping("/stockPrices/{stockPriceId}")
    public ResponseEntity<StockPrice> getStockPrice(@PathVariable Long stockPriceId) {
        StockPrice stockPrice = stockPriceService.getStockPrice(stockPriceId);
        return new ResponseEntity<StockPrice>(stockPrice, null, HttpStatus.OK);
    }

    // Delete StockPrice - {stockPriceId}
    @DeleteMapping("/stockPrices/{stockPriceId}")
    public ResponseEntity<HttpStatus> removeStockPrice(@PathVariable Long stockPriceId) {
        stockPriceService.removeStockPrice(stockPriceId);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

}
