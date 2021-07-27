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
    public Company newCompanyWithSE(Company company, String exchangeName, String sectorName) {
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
            if(!querySector.isPresent()) {
                System.out.println("NOT FOUND!!!!!");
                throw new RecordNotFoundException("Sector "+sectorName+" does not exist!");
            }
            System.out.println("SECTOR!!!!!");
            // Check: StockExchange
            Optional<StockExchange> queryStockExchange = stockExchangeRepository.findByName(exchangeName);
            if(!queryStockExchange.isPresent()) {
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
    public Company newCompany(Company company, String sectorName) {
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
            if(!querySector.isPresent()) {
                System.out.println("NOT FOUND!!!!!");
                throw new RecordNotFoundException("Sector "+sectorName+" does not exist!");
            }
            System.out.println("SECTOR!!!!!");

            Sector sector = querySector.get();

            Company newCompany = new Company(company);
            newCompany.setSector(sector);
            companyRepository.save(newCompany);
            sectorRepository.save(sector);
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
    public CompanyStockExchangeMap mapCompanyExchange(String companyName, String exchangeName, CompanyStockExchangeMap compSeMap) {
        try {
            Optional<Company> queryCompany = companyRepository.findByName(companyName);
            if (!queryCompany.isPresent()) {
                throw new BadRequestException("Company "+ companyName + " does not exist !!");
            }
            Optional<StockExchange> queryExchange = stockExchangeRepository.findByName(exchangeName);
            if(!queryExchange.isPresent()) {
                System.out.println("NOT FOUND!!!!!");
                throw new BadRequestException("SE "+exchangeName+" does not exist!");
            }

            Optional<CompanyStockExchangeMap>  queryMap = companyExchangeMapRepository.findByCompanyCode(compSeMap.getCompanyCode());
            if(queryMap.isPresent()) {
                System.out.println("NOT FOUND!!!!!");
                throw new BadRequestException("Company Code is already in use!");
            }

            Company company = queryCompany.get();
            StockExchange stockExchange = queryExchange.get();

            Optional<CompanyStockExchangeMap> queryCmSeMap = company.getCompStockMap().stream()
                    .filter( el -> exchangeName.equals(el.getStockExchange().getExchangeName()))
                    .findAny();
            if(queryCmSeMap.isPresent()) {
                System.out.println("ALREADY MAPPED");
                throw new BadRequestException("Company "+ companyName + " SE " + exchangeName +" are already mapped!");
            }


            // AUTO RANDOM UUID
//            String uniqueID = UUID.randomUUID().toString();

            CompanyStockExchangeMap newCompSeMap = new CompanyStockExchangeMap();
//            newCompSeMap.setCompanyCode(uniqueID);
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
    public Company getCompany(Long companyId) {
        try {
            Optional<Company> queryCompany = companyRepository.findById(companyId);
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
    public boolean isCompanyNameAvailable(String companyName) {
        try {
            Optional<Company> queryCompany = companyRepository.findByName(companyName);
            if (!queryCompany.isPresent()) {
                return true;
            }
            return false;
        }
        catch (Exception e) {
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
                System.out.println("LOL");
                System.out.println(company.getCompanyName());
                System.out.println(companyUpdateName);

                Optional<Company> queryCompanyByName = companyRepository.findByName(companyUpdateName);
                if(queryCompanyByName.isPresent()) {
                    throw new BadRequestException("Company Name "+companyUpdateName+ " is not available!");
                }
            }
            // Todo : Doubt : Do we have to cascade delete all companies if a sector is deleted
            // todo: or keep a value of null for company in sector field
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
            throw new InternalServerError("Something went wrong!!");
        }
    }




    @Override
    public void removeCompany(Long companyId) {
        try {
            Optional<Company> queryCompany = companyRepository.findById(companyId);
            if (!queryCompany.isPresent()) {
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
