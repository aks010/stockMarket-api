package com.stockmarket.company.service;

import com.stockmarket.company.entity.Company;
import com.stockmarket.company.entity.StockExchange;
import com.stockmarket.company.exceptions.BadRequestException;
import com.stockmarket.company.exceptions.InternalServerError;
import com.stockmarket.company.exceptions.RecordNotFoundException;
import com.stockmarket.company.repository.StockExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockExchangeService implements IStockExchangeService {
    @Autowired
    private StockExchangeRepository stockExchangeRepository;

    @Override
    public List<StockExchange> listStockExchanges() {
        return stockExchangeRepository.findAll();
    };

    @Override
    public StockExchange newStockExchange(StockExchange stockExchange) {
        try {
            Optional<StockExchange> queryObject = stockExchangeRepository.findByName(stockExchange.getExchangeName());
            if (queryObject.isPresent()) {
                throw new BadRequestException("StockExchange already registered!");
            }

            StockExchange newStockExchange = stockExchangeRepository.save(stockExchange);
            return newStockExchange;
        }
        catch(BadRequestException e) {
            throw e;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerError("Something went wrong!");
        }
    };

    @Override
    public List<Company> getCompanyList(String exchangeName) {
        try {

            Optional<StockExchange> queryObject = stockExchangeRepository.findByName(exchangeName);
            if (queryObject.isEmpty()) {
                throw new RecordNotFoundException("StockExchange "+exchangeName+ " does not exist!");
            }
            StockExchange stockExchange = queryObject.get();

            List<Company> companies = stockExchange.getCompStockMap().stream()
                    .map(compStockMap -> {
                        return compStockMap.getCompany();
                    }).collect(Collectors.toList());

            return companies;
        }
        catch(RecordNotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerError("Something went wrong!");
        }

    };


    @Override
    public StockExchange getStockExchange(String exchangeName) {
        try {

            Optional<StockExchange> queryObject = stockExchangeRepository.findByName(exchangeName);
            if (queryObject.isEmpty()) {
                throw new RecordNotFoundException("StockExchange "+exchangeName+ " does not exist!");
            }
            StockExchange stockExchange = queryObject.get();
            return stockExchange;
        }
        catch(RecordNotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerError("Something went wrong!");
        }
    };

    @Override
    public StockExchange updateStockExchange(String exchangeName, StockExchange stockExchange) {
        try {
            Optional<StockExchange> queryObject = stockExchangeRepository.findByName(stockExchange.getExchangeName());
            if (queryObject.isEmpty()) {
                throw new BadRequestException("StockExchange does not exist!");
            }

            String newName = stockExchange.getExchangeName();
            if(!newName.equals(exchangeName)) {
                Optional<StockExchange> queryExchange = stockExchangeRepository.findByName(newName);
                if (queryExchange.isPresent()) {
                    throw new BadRequestException("StockExchange Name "+newName + "is not available!!" );
                }
            }
            StockExchange queriedStockExchange = queryObject.get();
            queriedStockExchange.setExchangeName(stockExchange.getExchangeName());
            stockExchangeRepository.save(queriedStockExchange);

            return stockExchange;
        }
        catch(BadRequestException e) {
            throw e;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerError();
        }
    };

    @Override
    public void removeStockExchange(String exchangeName) {
        try {
            Optional<StockExchange> queryObject = stockExchangeRepository.findByName(exchangeName);
            if(queryObject.isEmpty()) {
                throw new BadRequestException("Stock Exchange " +exchangeName +" does not exist!");
            }
            StockExchange stockExchange = queryObject.get();
            stockExchangeRepository.deleteById(stockExchange.getId());
        }
        catch (BadRequestException e) {
            throw e;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerError();
        }
    };
}
