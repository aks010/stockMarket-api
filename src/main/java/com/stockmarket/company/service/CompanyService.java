package com.stockmarket.company.service;

import com.stockmarket.company.entity.Company;
import com.stockmarket.company.entity.CompanyStockExchangeMap;
import com.stockmarket.company.entity.Sector;
import com.stockmarket.company.entity.StockExchange;
import com.stockmarket.company.exceptions.BadRequestException;
import com.stockmarket.company.exceptions.InternalServerError;
import com.stockmarket.company.exceptions.RecordNotFoundException;
import com.stockmarket.company.repository.CompanyRepository;
import com.stockmarket.company.repository.CompanyStockExchangeMapRepository;
import com.stockmarket.company.repository.SectorRepository;
import com.stockmarket.company.repository.StockExchangeRepository;
import javassist.bytecode.ExceptionsAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CompanyService implements ICompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private StockExchangeRepository stockExchangeRepository;

    @Autowired
    private CompanyStockExchangeMapRepository companyExchangeMapRepository;

    @Override
    public Page<Company> listCompanies(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }
//
//    @Override
//    public List<Company> listCompanies() {
//        try{
//            return companyRepository.findAll();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            return null;
//
//        }
//
//    }

    @Override
    public Company newCompany(Company company, String exchangeName, String sectorName) {
        try {
            // Check: Duplicate
            System.out.println("CAAAAAAAAAAAALLLLLLLLLLLLLLLLLLLLLEEEEEEEEEEEEEEEEEEEEEDDDDDDDDDDDD!!!");
            System.out.println(company.getCompanyName());
            Optional<Company> queryCompany = companyRepository.findByName(company.getCompanyName());
            if (queryCompany.isPresent()) {
                throw new BadRequestException("Company "+ company.getCompanyName() + " is already registered");
            }

            // Check: Sector Exists
            Optional<Sector> querySector = sectorRepository.findByName(sectorName);
            if(querySector.isEmpty()) {
                System.out.println("NOT FOUND!!!!!");
                throw new RecordNotFoundException("Sector "+sectorName+" does not exist!");
            }
            System.out.println("SECTOR!!!!!");
            // Check: StockExchange
            Optional<StockExchange> queryStockExchange = stockExchangeRepository.findByName(exchangeName);
            if(queryStockExchange.isEmpty()) {
                System.out.println("SE NOT FOUND!!!!!");
                throw new RecordNotFoundException("Stock Exchange "+ exchangeName +" does not exist!");
            }

            System.out.println("SE!!!!!");

            Sector sector = querySector.get();
            StockExchange stockExchange = queryStockExchange.get();
            System.out.println("MAPPING SE!!!!!");

            Company newCompany = new Company(company);
            newCompany.setSector(sector);
            companyRepository.save(newCompany);
//
            System.out.println("MAPPING SE!!!!!");
            CompanyStockExchangeMap compStockMap = new CompanyStockExchangeMap();
            compStockMap.setCompany(newCompany);
            compStockMap.setStockExchange(stockExchange);
            System.out.println("MAPPED!!!!!");
            companyExchangeMapRepository.save(compStockMap);
            System.out.println("SAVING MEAPPA!!!!!");
            newCompany.addCompStockMap(compStockMap);
            stockExchange.addCompStockMap(compStockMap);
            System.out.println("MAINTAINING RELATIONS!!!!!");
            companyRepository.save(newCompany);
            sectorRepository.save(sector);
            stockExchangeRepository.save(stockExchange);
            System.out.println("RELATIONS MAINTAINED!!!!!");
            return newCompany;
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
            System.out.println("EROOROROROROROROROO!!!!!");
            System.out.println(e.getMessage());
            throw new InternalServerError("Something went wrong!!");
        }

    }

    @Override
    public Company getCompany(Long companyId) {
        try {
            Optional<Company> queryCompany = companyRepository.findById(companyId);
            if (queryCompany.isEmpty()) {
                throw new RecordNotFoundException();
            }
            return queryCompany.get();
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
    public Company updateCompany(Long companyId, Company userUpdate) {
        try {
            Optional<Company> queryCompany = companyRepository.findById(companyId);
            if (queryCompany.isEmpty()) {
                throw new RecordNotFoundException();
            }
            Company company = queryCompany.get();

            companyRepository.save(company);
            return queryCompany.get();
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
    public void removeCompany(Long companyId) {
        try {
            Optional<Company> queryCompany = companyRepository.findById(companyId);
            if (queryCompany.isEmpty()) {
                throw new RecordNotFoundException();
            }
            companyRepository.deleteById(companyId);
        }
        catch (RecordNotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerError("Something went wrong!!");
        }
    }
}
