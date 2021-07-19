package com.stockmarket.company;

import com.stockmarket.company.entity.*;
import com.stockmarket.company.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.Date;

// company code in STOCK PRICE is actually the ID of the company
// company code in COMPANYSTOCKExCAHGEMAP is actually stock code which is stock id


@SpringBootApplication
public class CompanyApplication implements CommandLineRunner {

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	StockExchangeRepository stockExchangeRepository;

	@Autowired
	CompanyStockExchangeMapRepository companyStockExchangeMapRepository;

	@Autowired
	StockPriceRepository stockPriceRepository;

	@Autowired
	SectorRepository sectorRepository;

	@Autowired
	IPODetailRepository ipoDetailRepository;

	public static void main(String[] args) {
		SpringApplication.run(CompanyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hello there! Lets Cool it up!!!");

		Company company = new Company("New Company", 732473426D, "metheceo", "hethedirector", "this is the brief");
		companyRepository.save(company);
		System.out.println("Created Company!!");

		StockExchange nse = new StockExchange("nse");
		StockExchange bse = new StockExchange("bse");
		stockExchangeRepository.save(nse);
		stockExchangeRepository.save(bse);

		CompanyStockExchangeMap cse = new CompanyStockExchangeMap();
		cse.setCompany(company);
		cse.setStockExchange(nse);
		cse.setCompanyCode("234");
		companyStockExchangeMapRepository.save(cse);

		StockPrice stockPrice = new StockPrice("nse", "234", new Date(), 5005);
		stockPrice.setCompany(company);
		stockPriceRepository.save(stockPrice);
		company.addStockPrices(stockPrice);
		companyRepository.save(company);

		Company company2 = new Company("New 2nsCompany", 732473426D, "metheceo", "hethedirector", "this is the brief");
		companyRepository.save(company2);

		Sector sector = new Sector("sector1");

		sector.addCompany(company2);

		sectorRepository.save(sector);

		company2.setSector(sector);
		companyRepository.save(company2);
		System.out.println("GOT COMPANIES!!");
		System.out.println(sector.getCompanies());

		IPODetail ipoDetail = new IPODetail(264724, 34125135L, LocalDateTime.now());
		ipoDetail.addStockExchange(nse);
		ipoDetail.addStockExchange(bse);
		ipoDetail.setCompany(company2);
		ipoDetailRepository.save(ipoDetail);
		company2.setIpoDetail(ipoDetail);
		nse.addIpoDetail(ipoDetail);
		stockExchangeRepository.save(nse);
		companyRepository.save(company2);
	}

}
