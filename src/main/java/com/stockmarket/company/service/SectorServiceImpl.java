package com.stockmarket.company.service;

import com.stockmarket.company.entity.Company;
import com.stockmarket.company.entity.Sector;
import com.stockmarket.company.exceptions.BadRequestException;
import com.stockmarket.company.exceptions.InternalServerError;
import com.stockmarket.company.exceptions.RecordNotFoundException;
import com.stockmarket.company.repository.CompanyRepository;
import com.stockmarket.company.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectorServiceImpl implements SectorService {
    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<Sector> listSectors() {
        return sectorRepository.findAll(Sort.by(Sort.Direction.ASC, "sectorName"));
    }

    @Override
    public List<Company> listSectorCompanies( String sectorName) {
        try {
            // Check: Duplicate
            Optional<Sector> querySector = sectorRepository.findByName(sectorName);
            if (!querySector.isPresent()) {
                throw new BadRequestException("Sector "+ sectorName + " does not exist");
            }
            Sector sector = querySector.get();
            List <Company> companies = sector.getCompanies();
            return companies;
        }
        catch (BadRequestException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerError("Something went wrong!!");
        }
    }

    @Override
    public Sector newSector(Sector sector) {
        try {
            // Check: Duplicate
            Optional<Sector> querySector = sectorRepository.findByName(sector.getSectorName());
            if (querySector.isPresent()) {
                throw new BadRequestException("Sector "+ sector.getSectorName() + " is already registered");
            }
            Sector newSector = new Sector();
            newSector.setSectorName(sector.getSectorName());
            newSector.setBrief(sector.getBrief());
            sectorRepository.save(newSector);
            return newSector;
        }
        catch (BadRequestException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerError("Something went wrong!!");
        }

    }

    @Override
    public boolean isSectorNameAvailable(String sectorName) {
        try {
            Optional<Sector> querySector = sectorRepository.findByName(sectorName);
            if (!querySector.isPresent()) {
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
    public Sector getSectorByName(String sectorName) {
        try {
            Optional<Sector> querySector = sectorRepository.findByName(sectorName);
            if (!querySector.isPresent()) {
                throw new RecordNotFoundException();
            }
            return querySector.get();
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
    public Sector updateSector(String sectorName, Sector sectorUpdate) {
        try {
            System.out.println(sectorName);
            Optional<Sector> querySector = sectorRepository.findByName(sectorName);
            if (!querySector.isPresent()) {
                throw new RecordNotFoundException();
            }
            Sector sector = querySector.get();

            String sectorUpdateName = sectorUpdate.getSectorName();
            if(!sector.getSectorName().equals(sectorUpdateName)) {
                Optional<Sector> querySectorByName = sectorRepository.findByName(sectorUpdateName);
                if(querySectorByName.isPresent()) {
                    throw new BadRequestException("Sector Name "+sectorUpdateName+ " is not available!");
                }
            }
            sector.setSectorName(sectorUpdateName);
            sector.setBrief(sectorUpdate.getBrief());
            sectorRepository.save(sector);
            return querySector.get();
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
    public void removeSector(String sectorName) {
        try {
            Optional<Sector> querySector = sectorRepository.findByName(sectorName);
            if (!querySector.isPresent()) {
                throw new RecordNotFoundException();
            }
            Sector sector = querySector.get();
            sectorRepository.deleteById(sector.getId());
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
