package com.stockmarket.company.service;

import com.stockmarket.company.entity.Company;
import com.stockmarket.company.entity.IPODetail;
import com.stockmarket.company.exceptions.BadRequestException;
import com.stockmarket.company.exceptions.InternalServerError;
import com.stockmarket.company.exceptions.RecordNotFoundException;
import com.stockmarket.company.repository.CompanyRepository;
import com.stockmarket.company.repository.IPODetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IPODetailService implements IIPODetailService {
    @Autowired
    private IPODetailRepository ipoDetailRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<IPODetail> listIPODetails() {
        return ipoDetailRepository.findAll();
    };

    @Override
    public IPODetail newIPODetail(String companyName, IPODetail ipoDetail) {
        try {
            Optional<Company> queryObject = companyRepository.findByName(companyName);
            if (queryObject.isEmpty()) {
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
            if (queryObject.isEmpty()) {
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
    // todo fix in se exchangeName
    @Override
    public IPODetail updateIPODetail(String companyName, IPODetail updateIpoDetail) {
        try {
            Optional<Company> queryObject = companyRepository.findByName(companyName);
            if (queryObject.isEmpty()) {
                throw new RecordNotFoundException("Company "+ companyName + " does not exist!!");
            }
            Company company = queryObject.get();
            if(company.getIpoDetail() == null) {
                throw new RecordNotFoundException("No IPO is registered under company " + companyName +"!!");
            }
            IPODetail ipoDetail = company.getIpoDetail();
            ipoDetail.setRemarks(ipoDetail.getRemarks());
            ipoDetail.setOpenDateTime(ipoDetail.getOpenDateTime());
            ipoDetail.setPricePerShare(ipoDetail.getPricePerShare());
            ipoDetail.setTotalNumberOfShares(ipoDetail.getTotalNumberOfShares());

            // todo updating ipo company
            ipoDetailRepository.save(ipoDetail);
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
            if (queryObject.isEmpty()) {
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
