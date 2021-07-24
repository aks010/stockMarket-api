package com.stockmarket.company.service;

import com.stockmarket.company.entity.*;
import com.stockmarket.company.exceptions.BadRequestException;
import com.stockmarket.company.exceptions.InternalServerError;
import com.stockmarket.company.exceptions.RecordNotFoundException;
import com.stockmarket.company.repository.CompanyRepository;
import com.stockmarket.company.repository.CompanyStockExchangeMapRepository;
import com.stockmarket.company.repository.StockExchangeRepository;
import com.stockmarket.company.repository.StockPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockPriceService implements IStockPriceService {
    @Autowired
    private StockPriceRepository stockPriceRepository;

    @Autowired
    private StockExchangeRepository stockExchangeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyStockExchangeMapRepository companyStockExchangeMapRepository;

    @Override
    public List<StockPrice> listStockPrices() {
      return stockPriceRepository.findAll();
    }

    @Override
    public List<Object> compareCompanies(CompareConfig compare) {
        System.out.println(compare);
        System.out.println(compare);

        try {

            LocalDate from = compare.from;
            LocalDate to = compare.to;
            System.out.println(from);
            System.out.println(to);
            List<Object> dataset = new ArrayList<>();
////
            compare.companyList.stream().forEach(o -> {
                System.out.println(o.getCompanyName());
                System.out.println(o.getExchangeName());
                Optional<Company> queryCompany = companyRepository.findByName(o.getCompanyName());
                if (queryCompany.isEmpty()) {
                    throw new BadRequestException("Company " + o.getCompanyName() + " does not exist!!");
                }
                Optional<StockExchange> queryExchange = stockExchangeRepository.findByName(o.getExchangeName());
                if (queryExchange.isEmpty()) {
                    throw new BadRequestException("Exchange " + o.getExchangeName() + " does not exist!!");
                }
                dataset.addAll(stockPriceRepository.findByDatee(compare.from, compare.to, o.getCompanyName(), o.getExchangeName()));
            });
//            compare.sectorList.stream().forEach(o -> {
//                Optional<Company> querySector = sectorRepository.findByName(o);
//                if (querySector.isEmpty()) {
//                    throw new BadRequestException("Sector " + o + " does not exist!!");
//                }
//                dataset.addAll(stockPriceRepository.findByDatee(compare.from, compare.to, o.getCompanyName(), o.getExchangeName()));
//            });
            return dataset;
        }
        catch(BadRequestException e) {throw e;}
        catch(Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerError("Something went wrong!");
        }
    }

    @Override
    public List<Company> listStockPriceCompanies( String stockPriceName) {
        try {
            // Check: Duplicate
//            Optional<StockPrice> queryStockPrice = stockPriceRepository.findByName(stockPriceName);
//            if (queryStockPrice.isEmpty()) {
//                throw new BadRequestException("StockPrice "+ stockPriceName + " does not exist");
//            }
//            StockPrice stockPrice = queryStockPrice.get();
//            System.out.println(stockPrice.getId());
//            List<Company> companies = companyRepository.findByStockPrice(stockPrice.getId());
//            return companies;
            return null;
        }
        catch (BadRequestException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerError("Something went wrong!!");
        }
    }

    @Override
    public void uploadExcel(List<StockPrice> stockPriceList) {
        try {
//            List<StockPrice> newStockPriceList = new ArrayList<>();

            stockPriceList.forEach( stockPrice -> {
                        String companyCode = stockPrice.getCompanyCode();
                        Optional<CompanyStockExchangeMap> queryCompSeMap = companyStockExchangeMapRepository.findByCompanyCode(companyCode);
                        if (queryCompSeMap.isEmpty()) {
                            throw new BadRequestException("Company Code does not exist!");
                        }

                        CompanyStockExchangeMap compSeMap = queryCompSeMap.get();
                        System.out.println(compSeMap.getCompanyCode());

                        String exchangeName = stockPrice.getExchangeName();
                        if(!exchangeName.equals(compSeMap.getStockExchange().getExchangeName())) {
                            System.out.println(exchangeName);
                            System.out.println(compSeMap.getStockExchange().getExchangeName());


                            throw new BadRequestException("No matching pair!!");
                        }

                        Optional<Company> queryCompany = companyRepository.findByName(compSeMap.getCompany().getCompanyName());
                        Company company = queryCompany.get();

                        StockPrice newStockPrice = new StockPrice();

                        newStockPrice.setSharePrice(stockPrice.getSharePrice());
                        newStockPrice.setCompanyCode(companyCode);
                        newStockPrice.setExchangeName(exchangeName);
                        newStockPrice.setDatee(stockPrice.getDatee());
                        newStockPrice.setTimee(stockPrice.getTimee());

                        newStockPrice.setCompany(company);
                        stockPriceRepository.save(newStockPrice);
//                        newStockPriceList.add(newStockPrice);
                        company.addStockPrices(newStockPrice);
                }
            );
        }
        catch (BadRequestException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        catch (RecordNotFoundException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerError("Something went wrong!!");
        }

    }


    @Override
    public StockPrice newStockPrice(StockPrice stockPrice, String companyName) {
        try {
            Optional<Company> queryCompany = companyRepository.findByName(companyName);
            if (queryCompany.isEmpty()) {
                throw new BadRequestException("Company "+ companyName + " does not exist!");
            }
            String exchangeName = stockPrice.getExchangeName();
            Optional<StockExchange> queryStockExchange = stockExchangeRepository.findByName(exchangeName);
            if(queryStockExchange.isEmpty()) {
                System.out.println("SE NOT FOUND!!!!!");
                throw new RecordNotFoundException("Stock Exchange "+ exchangeName +" does not exist!");
            }

            Company company = queryCompany.get();

            Optional<CompanyStockExchangeMap> queryCmSeMap = company.getCompStockMap().stream()
                    .filter( el -> exchangeName.equals(el.getStockExchange().getExchangeName()))
                    .findAny();
            if(queryCmSeMap.isEmpty()) {
                System.out.println("NO MAPPING FOR GIVEN COMPANY AND SE!!!!!");
                throw new BadRequestException("Company "+ companyName + " SE " + exchangeName +" are not mapped!");
            }

            String companyCode = queryCmSeMap.get().getCompanyCode();

            StockPrice newStockPrice = new StockPrice();

            newStockPrice.setSharePrice(stockPrice.getSharePrice());
            newStockPrice.setCompanyCode(companyCode);
            newStockPrice.setExchangeName(exchangeName);
            newStockPrice.setDatee(stockPrice.getDatee());
            newStockPrice.setTimee(stockPrice.getTimee());

            newStockPrice.setCompany(company);
            stockPriceRepository.save(newStockPrice);
            company.addStockPrices(newStockPrice);
            return newStockPrice;
        }
        catch (BadRequestException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        catch (RecordNotFoundException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerError("Something went wrong!!");
        }

    }

    // TODO: getStockPricePrice StockPrice ID, From Period, To period, periodicity

    @Override
    public StockPrice getStockPrice(Long stockPriceId) {
        try {
            Optional<StockPrice> queryStockPrice = stockPriceRepository.findById(stockPriceId);
            if (queryStockPrice.isEmpty()) {
                throw new RecordNotFoundException();
            }
            return queryStockPrice.get();
        }
        catch (RecordNotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerError("Something went wrong!!");
        }

    }

    @Override
    public StockPrice getStockPriceByName(String stockPriceName) {
        try {
//            Optional<StockPrice> queryStockPrice = stockPriceRepository.findByName(stockPriceName);
//            if (queryStockPrice.isEmpty()) {
//                throw new RecordNotFoundException();
//            }
//            return queryStockPrice.get();
            return null;
        }
        catch (RecordNotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerError("Something went wrong!!");
        }
    }

    @Override
    public StockPrice updateStockPrice(Long stockPriceId, StockPrice stockPriceUpdate) {
        try {
            Optional<StockPrice> queryStockPrice = stockPriceRepository.findById(stockPriceId);
            if (queryStockPrice.isEmpty()) {
                throw new RecordNotFoundException();
            }
            StockPrice stockPrice = queryStockPrice.get();

//            String stockPriceUpdateName = stockPriceUpdate.getStockPriceName();
//            if(stockPrice.getStockPriceName()!=stockPriceUpdateName) {
//                Optional<StockPrice> queryStockPriceByName = stockPriceRepository.findByName(stockPriceUpdateName);
//                if(queryStockPriceByName.isPresent()) {
//                    throw new BadRequestException("StockPrice Name "+stockPriceUpdateName+ " is not available!");
//                }
//            }

//            stockPrice.setStockPriceName(stockPriceUpdateName);
//            stockPriceRepository.save(stockPrice);
//            return queryStockPrice.get();
            return null;
        }
        catch (BadRequestException e) {
            throw e;
        }
        catch (RecordNotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerError("Something went wrong!!");
        }
    }

    @Override
    public void removeStockPrice(Long stockPriceId) {
        try {
            Optional<StockPrice> queryStockPrice = stockPriceRepository.findById(stockPriceId);
            if (queryStockPrice.isEmpty()) {
                throw new RecordNotFoundException();
            }
            stockPriceRepository.deleteById(stockPriceId);
        }
        catch (RecordNotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerError("Something went wrong!!");
        }
    }


//    @Override
//    public List<StockPrice> companyCompare(CompanyCompareList companyList) {
//        try {
//            Optional<Company> queryCompany = companyRepository.findByName(companyName);
//            if (queryCompany.isEmpty()) {
//                throw new BadRequestException("Company " + companyName + " does not exist!");
//            }

//            Company company = queryCompany.get();
//            //
//            temp;
//            company.getStockPrices().stream()


//
//        }
//
//    }
//
}
