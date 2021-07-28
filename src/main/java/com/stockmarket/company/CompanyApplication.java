package com.stockmarket.company;

import com.stockmarket.company.entity.*;
import com.stockmarket.company.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.util.Optional;

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

	@Autowired
	MyUserRepository myUserRepository;

	public static void main(String[] args) {
		SpringApplication.run(CompanyApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.out.println("Hello there! Lets Cool it up!!!");

		MyUser myUser = new MyUser("admin",
				"admin",
				"admin@gmail.com",
				"admin",
				"7894567894", true, true);
		myUserRepository.save(myUser);

		Sector sector = new Sector("Health", "brief of sector Health");
		sectorRepository.save(sector);

		sector = new Sector("Finance", "brief of sector Finance");
		sectorRepository.save(sector);

		sector = new Sector("Technology", "brief of sector Technology");
		sectorRepository.save(sector);


		StockExchange nse = new StockExchange("NSE", "brief about NSE", "Mumbai", "good");
		StockExchange bse = new StockExchange("BSE", "brief about BSE","Mumbai", "good");
		stockExchangeRepository.save(nse);
		stockExchangeRepository.save(bse);



		Optional<Sector> querysector = sectorRepository.findByName("Technology");

		sector = querysector.get();

		Company company = new Company();
		company.setCompanyName("A");
		company.setSector(sector);
		company.setCeo("ceo");
		company.setBoardOfDirectors("bod");
		company.setCompanyBrief("");
		company.setTurnover(23123123D);
		companyRepository.save(company);
		sector.addCompany(company);
		sectorRepository.save(sector);

//		Optional<Company> querycompany = companyRepository.findByName("A");
//		company = querycompany.get();
//		Optional<StockExchange> queryexchange = stockExchangeRepository.findByName("BSE");
//		bse = queryexchange.get();
//		System.out.println(bse);
//		CompanyStockExchangeMap cmap = new CompanyStockExchangeMap();
//		cmap.setCompany(company);
//		cmap.setStockExchange(bse);
//		cmap.setCompanyCode("500112");
//		companyStockExchangeMapRepository.save(cmap);
//
//		bse.addCompStockMap(cmap);
//		company.addCompStockMap(cmap);
//		companyRepository.save(company);
//		stockExchangeRepository.save(bse);


//		company2.setSector(sector);
//		companyRepository.save(company2);
//		System.out.println("GOT COMPANIES!!");
//		System.out.println(sector.getCompanies());
//
//		IPODetail ipoDetail = new IPODetail(264724, 34125135L, LocalDateTime.now());
//		ipoDetail.addStockExchange(nse);
//		ipoDetail.addStockExchange(bse);
//		ipoDetail.setCompany(company2);
//		ipoDetailRepository.save(ipoDetail);
//		company2.setIpoDetail(ipoDetail);
//		nse.addIpoDetail(ipoDetail);
//		stockExchangeRepository.save(nse);
//		companyRepository.save(company2);
	}

}
