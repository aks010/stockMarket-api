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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private StockExchangeRepository stockExchangeRepository;

    @Autowired
    private CompanyStockExchangeMapRepository companyExchangeMapRepository;

    @Override
    public List<Company> listCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public List<Company> listCompaniesByPattern(String startsWith) {
        return companyRepository.findByNameStartWith(startsWith);
    }

    @Override
    public Company newCompany(Company company, String sectorName) {
        try {
            // Check: Duplicate
            Optional<Company> queryCompany = companyRepository.findByName(company.getCompanyName());
            if (queryCompany.isPresent()) {
                throw new BadRequestException("Company "+ company.getCompanyName() + " is already registered");
            }

            // Check: Sector Exists
            Optional<Sector> querySector = sectorRepository.findByName(sectorName);
            if(!querySector.isPresent()) {
                System.out.println("NOT FOUND!!!!!");
                throw new RecordNotFoundException("Sector "+sectorName+" does not exist!");
            }
            Sector sector = querySector.get();

            Company newCompany = new Company(company);
            newCompany.setSector(sector);
            companyRepository.save(newCompany);
            sectorRepository.save(sector);
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
            System.out.println(e.getMessage());
            throw new InternalServerError("Something went wrong!!");
        }

    }

    @Override
    public CompanyStockExchangeMap mapCompanyExchange(String companyName, String exchangeName, CompanyStockExchangeMap compSeMap) {
        try {
            Optional<Company> queryCompany = companyRepository.findByName(companyName);
            if (!queryCompany.isPresent()) {
                throw new BadRequestException("Company "+ companyName + " does not exist !!");
            }
            Optional<StockExchange> queryExchange = stockExchangeRepository.findByName(exchangeName);
            if(!queryExchange.isPresent()) {
                throw new BadRequestException("SE "+exchangeName+" does not exist!");
            }

            Optional<CompanyStockExchangeMap>  queryMap = companyExchangeMapRepository.findByCompanyCode(compSeMap.getCompanyCode());
            if(queryMap.isPresent()) {
                throw new BadRequestException("Company Code is already in use!");
            }

            Company company = queryCompany.get();
            StockExchange stockExchange = queryExchange.get();

            Optional<CompanyStockExchangeMap> queryCmSeMap = company.getCompStockMap().stream()
                    .filter( el -> exchangeName.equals(el.getStockExchange().getExchangeName()))
                    .findAny();
            if(queryCmSeMap.isPresent()) {
                throw new BadRequestException("Company "+ companyName + " SE " + exchangeName +" are already mapped!");
            }

            CompanyStockExchangeMap newCompSeMap = new CompanyStockExchangeMap();
            newCompSeMap.setCompanyCode(compSeMap.getCompanyCode());
            newCompSeMap.setCompany(company);

            newCompSeMap.setStockExchange(stockExchange);
            companyExchangeMapRepository.save(newCompSeMap);

            company.addCompStockMap(newCompSeMap);
            stockExchange.addCompStockMap(newCompSeMap);

            companyRepository.save(company);
            stockExchangeRepository.save(stockExchange);

            return newCompSeMap;
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
    public Company getCompanyByName(String companyName) {
        try {
            Optional<Company> queryCompany = companyRepository.findByName(companyName);
            if (!queryCompany.isPresent()) {
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
    public Company updateCompany(String companyName, Company companyUpdate) {
        try {
            Optional<Company> queryCompany = companyRepository.findByName(companyName);
            if (!queryCompany.isPresent()) {
                throw new RecordNotFoundException();
            }
            Company company = queryCompany.get();

            String companyUpdateName = companyUpdate.getCompanyName();
            String companyNameInDB = company.getCompanyName();
            if(!companyNameInDB.equals(companyUpdateName)) {
                System.out.println(company.getCompanyName());
                System.out.println(companyUpdateName);

                Optional<Company> queryCompanyByName = companyRepository.findByName(companyUpdateName);
                if(queryCompanyByName.isPresent()) {
                    throw new BadRequestException("Company Name "+companyUpdateName+ " is not available!");
                }
            }

            // Check: Sector Exists
            String sectorName = companyUpdate.getSector().getSectorName();
            Optional<Sector> querySector = sectorRepository.findByName(sectorName);
            if(!querySector.isPresent()) {
                System.out.println("NOT FOUND!!!!!");
                throw new RecordNotFoundException("Sector "+sectorName+" does not exist!");
            }

            company.setCompanyBrief(companyUpdate.getCompanyBrief());
            company.setCompanyName(companyUpdate.getCompanyName());
            company.setTurnover(companyUpdate.getTurnover());
            company.setSector(querySector.get());
            company.setBoardOfDirectors(companyUpdate.getBoardOfDirectors());
            company.setCeo(companyUpdate.getCeo());

            companyRepository.save(company);
            return queryCompany.get();
        }
        catch (BadRequestException e) {
            throw e;
        }
        catch (RecordNotFoundException e) {
            throw e;
        }
        catch (Exception e) {

            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new InternalServerError("Something went wrong!!");
        }
    }

    @Override
    public void removeCompany(String companyName) {
        try {
            Optional<Company> queryCompany = companyRepository.findByName(companyName);
            if (!queryCompany.isPresent()) {
                throw new RecordNotFoundException();
            }
            companyRepository.deleteById(queryCompany.get().getId());
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
