package com.stockmarket.company.controller;

import com.stockmarket.company.entity.IPODetail;
import com.stockmarket.company.exceptions.BadRequestException;
import com.stockmarket.company.service.IPODetailService;
import com.stockmarket.company.service.IPODetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class IPODetailController {

    @Autowired
    private IPODetailService ipoDetailService;

    // todo: list of companies in a stock exchange

    @GetMapping("/ipo/list")
    public List<IPODetail> listIPODetails() {
        return ipoDetailService.listIPODetails();
    }
    @GetMapping("/ipo/list/upcoming")
    public List<IPODetail> listUpcoming() {
        return ipoDetailService.listUpcoming();
    }

    @PostMapping("/ipo/new/{companyName}")
    public ResponseEntity<IPODetail> newIPODetail(@PathVariable String companyName, @Valid @RequestBody IPODetail ipoDetail, BindingResult bindingResult) {
            if(bindingResult.hasErrors()) {
                throw new BadRequestException("Please submit with valid entries!");
            }
            IPODetail newIPODetail = ipoDetailService.newIPODetail(companyName, ipoDetail);
            return new ResponseEntity<IPODetail>(newIPODetail, null, HttpStatus.CREATED);
    }

    @GetMapping("/ipo/{companyName}")
    public ResponseEntity<IPODetail> getIPODetail(@PathVariable String companyName) {
        IPODetail ipoDetail = ipoDetailService.getIPODetail(companyName);
        return new ResponseEntity<IPODetail>(ipoDetail, null, HttpStatus.OK);
    }
    @GetMapping("/ipo/map/{companyName}/{exchangeName}")
    public ResponseEntity<IPODetail> mapIpoExchange(@PathVariable String companyName, @PathVariable String exchangeName) {
        IPODetail ipoDetail = ipoDetailService.mapIpoExchange(companyName, exchangeName);
        return new ResponseEntity<IPODetail>(ipoDetail, null, HttpStatus.OK);
    }

    @PutMapping("/ipo/update/{companyName}")
    public ResponseEntity<IPODetail> updateIPODetail(@PathVariable String companyName, @Valid @RequestBody IPODetail ipoDetail, BindingResult bindingResult) {
        IPODetail updatedIPODetail = ipoDetailService.updateIPODetail(companyName, ipoDetail);
        return new ResponseEntity<IPODetail>(updatedIPODetail, null, HttpStatus.OK);
    }

    @DeleteMapping("/ipo/{companyName}")
    public ResponseEntity<HttpStatus> removeIPODetail(@PathVariable String exchangeName) {
        ipoDetailService.removeIPODetail(exchangeName);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
}
