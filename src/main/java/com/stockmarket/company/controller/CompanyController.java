package com.stockmarket.company.controller;

import com.stockmarket.company.entity.CompanyStockExchangeMap;
import com.stockmarket.company.service.CompanyService;
import com.stockmarket.company.entity.Company;
import com.stockmarket.company.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    // List Existing Companies
    @GetMapping("/company/list")
    public List<Company> listCompanies() {
        return companyService.listCompanies();
    }

    // List Existing Companies
    @GetMapping("/company/list/pattern/{companyPattern}")
    public List<Company> listCompaniesByPattern(@PathVariable String companyPattern) {
        return companyService.listCompaniesByPattern(companyPattern);
    }


    @PostMapping("/company/map/{companyName}/{exchangeName}")
    public ResponseEntity<CompanyStockExchangeMap> mapCompanyExchange(@PathVariable String companyName, @PathVariable String exchangeName, @Valid @RequestBody CompanyStockExchangeMap compSeMap, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new BadRequestException("Please submit with valid entries!");
        }
        System.out.println(bindingResult);
        CompanyStockExchangeMap newCompSeMap = companyService.mapCompanyExchange(companyName, exchangeName, compSeMap);
        return new ResponseEntity<CompanyStockExchangeMap>(newCompSeMap, null, HttpStatus.CREATED);
    }

    // Create new Company without SE
    @PostMapping("/company/new/{sectorName}")
    public ResponseEntity<Company> newCompany(@PathVariable String sectorName, @Valid @RequestBody Company company, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new BadRequestException("Please submit with valid entries!");
        }
        System.out.println(bindingResult);
        Company newCompany = companyService.newCompany(company, sectorName);
        return new ResponseEntity<Company>(newCompany, null, HttpStatus.CREATED);
    }


    // Get a company by {companyName}
    @GetMapping("/company/name/{companyName}")
    public ResponseEntity<Company> getCompanyByName(@PathVariable String companyName) {
        Company company = companyService.getCompanyByName(companyName);
        return new ResponseEntity<Company>(company, null, HttpStatus.OK);
    }



//    // Check a company name is available for registration - {companyName}
//    @GetMapping("/company/check/{companyName}")
//    public ResponseEntity<HttpStatus> isCompanyNameAvailable(@PathVariable String companyName) {
//        if(companyService.isCompanyNameAvailable(companyName)) {
//            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
//        }
//        else {
//            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
//        }
//    }

//    // Get a company by {companyId}
//    @GetMapping("/company/{companyId}")
//    public ResponseEntity<Company> getCompany(@PathVariable Long companyId) {
//        Company company = companyService.getCompany(companyId);
//        return new ResponseEntity<Company>(company, null, HttpStatus.OK);
//    }

    // Update Company - {companyId}
    @PutMapping("/company/update/{companyName}")
    public ResponseEntity<Company> updateCompany(@PathVariable String companyName, @Valid @RequestBody Company company, BindingResult bindingResult) {
        Company updatedCompany = companyService.updateCompany(companyName, company);
        return new ResponseEntity<Company>(updatedCompany, null, HttpStatus.OK);
    }

    // Delete Company - {companyId}
    @DeleteMapping("/company/{companyId}")
    public ResponseEntity<HttpStatus> removeCompany(@PathVariable Long companyId) {
        companyService.removeCompany(companyId);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    // TODO: List StockExchanges with company listed in

////     Create new Company along with exchange & sector
//    @PostMapping("/company/new/{exchangeName}/{sectorName}")
//    public ResponseEntity<Company> newCompanyWithSE(@PathVariable String exchangeName, @PathVariable String sectorName, @Valid @RequestBody Company company, BindingResult bindingResult) {
//            if(bindingResult.hasErrors()) {
//                throw new BadRequestException("Please submit with valid entries!");
//            }
//            System.out.println(bindingResult);
//            Company newCompany = companyService.newCompanyWithSE(company, exchangeName, sectorName);
//            return new ResponseEntity<Company>(newCompany, null, HttpStatus.CREATED);
//    }

}
