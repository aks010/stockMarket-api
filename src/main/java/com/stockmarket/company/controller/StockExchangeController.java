package com.stockmarket.company.controller;

import com.stockmarket.company.entity.Company;
import com.stockmarket.company.entity.StockExchange;
import com.stockmarket.company.exceptions.BadRequestException;
import com.stockmarket.company.service.StockExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class StockExchangeController {

    @Autowired
    private StockExchangeService stockExchangeService;

    // todo: list of companies in a stock exchange

    // ADMIN: List Existing Stock Exchanges
    @GetMapping("/stockExchange/list")
    public List<StockExchange> listStockExchanges() {
        return stockExchangeService.listStockExchanges();
    }

    @GetMapping("/stockExchange/list/{exchangeName}")
    public List<Company> getCompanyList(@PathVariable String exchangeName) {
        return stockExchangeService.getCompanyList(exchangeName);
    }

    // ADMIN: Create new Stock Exchange
    @PostMapping("/stockExchange/new")
    public ResponseEntity<StockExchange> newStockExchange(@Valid @RequestBody StockExchange stockExchange, BindingResult bindingResult) {
            if(bindingResult.hasErrors()) {
                throw new BadRequestException("Please submit with valid entries!");
            }

            System.out.println(bindingResult);
            System.out.println("Reached Here!!!");
            StockExchange newStockExchange = stockExchangeService.newStockExchange(stockExchange);
            return new ResponseEntity<StockExchange>(newStockExchange, null, HttpStatus.CREATED);
    }

    // ADMIN: Get a Stock Exchange by ID
    @GetMapping("/stockExchange/{exchangeName}")
    public ResponseEntity<StockExchange> getStockExchange(@PathVariable String exchangeName) {
        StockExchange stockExchange = stockExchangeService.getStockExchange(exchangeName);
        return new ResponseEntity<StockExchange>(stockExchange, null, HttpStatus.OK);
    }

    // ADMIN: Update Stock Exchange
    @PutMapping("/stockExchange/update/{exchangeName}")
    public ResponseEntity<StockExchange> updateStockExchange(@PathVariable String exchangeName, @Valid @RequestBody StockExchange stockExchange, BindingResult bindingResult) {
        StockExchange updatedStockExchange = stockExchangeService.updateStockExchange(exchangeName, stockExchange);
        return new ResponseEntity<StockExchange>(updatedStockExchange, null, HttpStatus.OK);
    }

    // ADMIN: Delete Stock Exchange - {exchangeName}
    @DeleteMapping("/stockExchange/{exchangeName}")
    public ResponseEntity<HttpStatus> removeStockExchange(@PathVariable String exchangeName) {
        stockExchangeService.removeStockExchange(exchangeName);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
}
