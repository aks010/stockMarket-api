package com.stockmarket.company.controller;

import com.stockmarket.company.service.CompanyService;
import com.stockmarket.company.entity.Company;
import com.stockmarket.company.exceptions.BadRequestException;
import com.stockmarket.company.service.CompanyService;
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
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    // List Existing Companies
    @GetMapping("/company/list")
    public Page<Company> listCompanies(Pageable pageable) {
        return companyService.listCompanies(pageable);
    }

//
//    // List Existing Companies
//    @GetMapping("/company/list")
//    public ResponseEntity<List<Company>> listCompanies() {
//        try {
//            return new ResponseEntity<List<Company>>(companyService.listCompanies(),null, HttpStatus.OK);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("CAUGGGGHTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT@!!");
//            return null;
//        }
//
//    }

    // Create new Company
    @PostMapping("/company/new/{exchangeName}/{sectorName}")
    public ResponseEntity<Company> newCompany(@PathVariable String exchangeName, @PathVariable String sectorName, @Valid @RequestBody Company company, BindingResult bindingResult) {
            if(bindingResult.hasErrors()) {
                throw new BadRequestException("Please submit with valid entries!");
            }

            System.out.println(bindingResult);

            Company newCompany = companyService.newCompany(company, exchangeName, sectorName);
            return new ResponseEntity<Company>(newCompany, null, HttpStatus.CREATED);
    }

    // Get a user by {companyId}
    @GetMapping("/company/{companyId}")
    public ResponseEntity<Company> getCompany(@PathVariable Long companyId) {
        Company company = companyService.getCompany(companyId);
        return new ResponseEntity<Company>(company, null, HttpStatus.OK);
    }

    // Update Company - {companyId}
    @PutMapping("/company/update/{companyId}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long companyId, @Valid @RequestBody Company company, BindingResult bindingResult) {
        Company updatedCompany = companyService.updateCompany(companyId, company);
        return new ResponseEntity<Company>(updatedCompany, null, HttpStatus.OK);
    }

    // Delete Company - {companyId}
    @DeleteMapping("/company/{companyId}")
    public ResponseEntity<HttpStatus> removeCompany(@PathVariable Long companyId) {
        companyService.removeCompany(companyId);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

}
