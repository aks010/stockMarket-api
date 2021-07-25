package com.stockmarket.company.service;

import com.stockmarket.company.entity.Company;
import com.stockmarket.company.entity.Sector;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISectorService {
    public List<Sector> listSectors();
    public List<Company> listSectorCompanies( String sectorName);
    public Sector newSector(Sector company);
    public boolean isSectorNameAvailable(String sectorName);
    public Sector getSectorByName(String sectorName);
    public Sector updateSector(String sectorName, Sector sector);
    public void removeSector(String sectorName);
}
