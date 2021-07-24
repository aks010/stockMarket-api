package com.stockmarket.company.controller;

import com.stockmarket.company.entity.Company;
import com.stockmarket.company.entity.CompareConfig;
import com.stockmarket.company.entity.StockPrice;
import com.stockmarket.company.exceptions.BadRequestException;
import com.stockmarket.company.service.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/stockPrices/companies/{stockPriceName}")
    public List<Company> listStockPriceCompanies(@PathVariable String stockPriceName) {
        return stockPriceService.listStockPriceCompanies( stockPriceName);
    }

    // Create new StockPrice without SE
    @PostMapping("/stockPrices/new/{companyName}")
    public ResponseEntity<StockPrice> newStockPrice(@PathVariable String companyName, @Valid @RequestBody StockPrice stockPrice, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new BadRequestException("Please submit with valid entries!");
        }
        System.out.println(bindingResult);
        StockPrice newStockPrice = stockPriceService.newStockPrice(stockPrice, companyName);
        return new ResponseEntity<StockPrice>(newStockPrice, null, HttpStatus.CREATED);
    }

    // upload Stock Price Excel
    @PostMapping("/stockPrices/uploadExcel")
    public ResponseEntity<HttpStatus> newStockPrice(@Valid @RequestBody List<StockPrice> stockPriceList, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new BadRequestException("Please submit with valid entries!");
        }
        System.out.println(bindingResult);
        stockPriceService.uploadExcel(stockPriceList);
        return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
    }

    // Get a stockPrice by {stockPriceId}
    @GetMapping("/stockPrices/{stockPriceId}")
    public ResponseEntity<StockPrice> getStockPrice(@PathVariable Long stockPriceId) {
        StockPrice stockPrice = stockPriceService.getStockPrice(stockPriceId);
        return new ResponseEntity<StockPrice>(stockPrice, null, HttpStatus.OK);
    }

    // Update StockPrice - {stockPriceId}
    @PutMapping("/stockPrices/update/{stockPriceId}")
    public ResponseEntity<StockPrice> updateStockPrice(@PathVariable Long stockPriceId, @Valid @RequestBody StockPrice stockPrice, BindingResult bindingResult) {
        StockPrice updatedStockPrice = stockPriceService.updateStockPrice(stockPriceId, stockPrice);
        return new ResponseEntity<StockPrice>(updatedStockPrice, null, HttpStatus.OK);
    }

    // Delete StockPrice - {stockPriceId}
    @DeleteMapping("/stockPrices/{stockPriceId}")
    public ResponseEntity<HttpStatus> removeStockPrice(@PathVariable Long stockPriceId) {
        stockPriceService.removeStockPrice(stockPriceId);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }



//    // COMPARE
//    @PostMapping("/stockPrices/compare/company")
//    public ResponseEntity<StockPrice> compareCompany(@Valid @RequestBody CompanyCompareList, BindingResult bindingResult) {
//        if(bindingResult.hasErrors()) {
//            throw new BadRequestException("Please submit with valid entries!");
//        }
//        System.out.println(bindingResult);
//        StockPrice newStockPrice = stockPriceService.compareCompany(CompanyCompareList);
//        return new ResponseEntity<StockPrice>(newStockPrice, null, HttpStatus.CREATED);
//        return null;
//    }
}
