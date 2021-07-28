package com.stockmarket.company.service;

import com.stockmarket.company.entity.Company;
import com.stockmarket.company.entity.IPODetail;
import com.stockmarket.company.entity.StockExchange;
import com.stockmarket.company.exceptions.BadRequestException;
import com.stockmarket.company.exceptions.InternalServerError;
import com.stockmarket.company.exceptions.RecordNotFoundException;
import com.stockmarket.company.repository.CompanyRepository;
import com.stockmarket.company.repository.IPODetailRepository;
import com.stockmarket.company.repository.StockExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class IPODetailServiceImpl implements IPODetailService {
    @Autowired
    private IPODetailRepository ipoDetailRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private StockExchangeRepository stockExchangeRepository;
    @Override
    public List<IPODetail> listIPODetails() {
        return ipoDetailRepository.findAll();
    };

    @Override
    public List<IPODetail> listUpcoming() {
        LocalDateTime present = LocalDateTime.now();
        return ipoDetailRepository.findByOpenDateTime(present);
    };

    @Override
    public IPODetail newIPODetail(String companyName, IPODetail ipoDetail) {
        try {
            Optional<Company> queryObject = companyRepository.findByName(companyName);
            if (!queryObject.isPresent()) {
                throw new BadRequestException("Company "+ companyName + " does not exist!!");
            }

            Company company = queryObject.get();
            if(company.getIpoDetail() != null) {
                throw new BadRequestException("IPO under company " + companyName + " is already registered!!");
            }

            IPODetail newIpoDetail = new IPODetail();
            newIpoDetail.setRemarks(ipoDetail.getRemarks());
            newIpoDetail.setOpenDateTime(ipoDetail.getOpenDateTime());
            newIpoDetail.setPricePerShare(ipoDetail.getPricePerShare());
            newIpoDetail.setTotalNumberOfShares(ipoDetail.getTotalNumberOfShares());
            newIpoDetail.setCompany(company);
            ipoDetailRepository.save(newIpoDetail);
            company.setIpoDetail(newIpoDetail);
            companyRepository.save(company);
            return newIpoDetail;
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
    public IPODetail getIPODetail(String companyName) {
        try {
            Optional<Company> queryObject = companyRepository.findByName(companyName);
            if (!queryObject.isPresent()) {
                throw new RecordNotFoundException("Company "+ companyName + " does not exist!!");
            }

            Company company = queryObject.get();
            if(company.getIpoDetail() == null) {
                throw new RecordNotFoundException("No IPO is registered under company " + companyName +"!!");
            }
            return company.getIpoDetail();
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
    public IPODetail mapIpoExchange(String companyName, String exchangeName) {
        try {
            Optional<Company> queryObject = companyRepository.findByName(companyName);
            if (!queryObject.isPresent()) {
                throw new RecordNotFoundException("Company "+ companyName + " does not exist!!");
            }
            Optional<StockExchange> queryExchange = stockExchangeRepository.findByName(exchangeName);
            if (!queryExchange.isPresent()) {
                throw new RecordNotFoundException("Exchange "+ exchangeName + " does not exist!!");
            }

            Company company = queryObject.get();
            if(company.getIpoDetail() == null) {
                throw new RecordNotFoundException("No IPO is registered under company " + companyName +"!!");
            }

            StockExchange stockExchange = queryExchange.get();
            IPODetail ipoDetail = company.getIpoDetail();



            Optional<StockExchange> result = ipoDetail.getStockExchanges().stream().filter(o -> o.getExchangeName().equals(exchangeName)).findAny();
            if(result.isPresent()) {
                System.out.println(result.get().getExchangeName());
                throw new BadRequestException("IPO is already added to this stock exchange!");
            }

            ipoDetail.addStockExchange(stockExchange);
            stockExchange.addIpoDetail(ipoDetail);
            stockExchangeRepository.save(stockExchange);
            ipoDetailRepository.save(ipoDetail);
            return ipoDetail;
        }
        catch(RecordNotFoundException e) {
            throw e;
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
    public IPODetail updateIPODetail(String companyName, IPODetail updateIpoDetail) {
        try {
            Optional<Company> queryObject = companyRepository.findByName(companyName);
            if (!queryObject.isPresent()) {
                throw new RecordNotFoundException("Company "+ companyName + " does not exist!!");
            }
            Company company = queryObject.get();
            if(company.getIpoDetail() == null) {
                throw new RecordNotFoundException("No IPO is registered under company " + companyName +"!!");
            }
            IPODetail ipoDetail = company.getIpoDetail();
            ipoDetail.setRemarks(updateIpoDetail.getRemarks());
            ipoDetail.setOpenDateTime(updateIpoDetail.getOpenDateTime());
            ipoDetail.setPricePerShare(updateIpoDetail.getPricePerShare());
            ipoDetail.setTotalNumberOfShares(updateIpoDetail.getTotalNumberOfShares());

            ipoDetail = ipoDetailRepository.save(ipoDetail);
            company.setIpoDetail(ipoDetail);
            companyRepository.save(company);
            return ipoDetail;
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
    public void removeIPODetail(String companyName) {
        try {
            Optional<Company> queryObject = companyRepository.findByName(companyName);
            if (!queryObject.isPresent()) {
                throw new RecordNotFoundException("Company "+ companyName + " does not exist!!");
            }
            Company company = queryObject.get();
            if(company.getIpoDetail() == null) {
                throw new RecordNotFoundException("No IPO is registered under company " + companyName +"!!");
            }
            IPODetail ipoDetail = company.getIpoDetail();
            ipoDetailRepository.delete(ipoDetail);
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
