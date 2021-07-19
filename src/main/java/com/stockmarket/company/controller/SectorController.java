package com.stockmarket.company.controller;

import com.stockmarket.company.entity.Company;
import com.stockmarket.company.entity.Sector;
import com.stockmarket.company.exceptions.BadRequestException;
import com.stockmarket.company.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SectorController {

    @Autowired
    private SectorService sectorService;

    @GetMapping("/sectors/list")
    public List<Sector> listSectors() {
        return sectorService.listSectors();
    }

    @GetMapping("/sectors/companies/{sectorName}")
        public List<Company> listSectorCompanies(@PathVariable String sectorName) {
        return sectorService.listSectorCompanies( sectorName);
    }

    // Create new Sector without SE
    @PostMapping("/sectors/new")
    public ResponseEntity<Sector> newSector(@Valid @RequestBody Sector sector, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new BadRequestException("Please submit with valid entries!");
        }
        System.out.println(bindingResult);
        Sector newSector = sectorService.newSector(sector);
        return new ResponseEntity<Sector>(newSector, null, HttpStatus.CREATED);
    }

    // Get a sector by {sectorName}
    @GetMapping("/sectors/name/{sectorName}")
    public ResponseEntity<Sector> getSectorByName(@PathVariable String sectorName) {
        Sector sector = sectorService.getSectorByName(sectorName);
        return new ResponseEntity<Sector>(sector, null, HttpStatus.OK);
    }

    // Get a sector by {sectorId}
    @GetMapping("/sectors/{sectorId}")
    public ResponseEntity<Sector> getSector(@PathVariable Long sectorId) {
        Sector sector = sectorService.getSector(sectorId);
        return new ResponseEntity<Sector>(sector, null, HttpStatus.OK);
    }

    // Check a sector name is available for registration - {sectorName}
    @GetMapping("/sectors/check/{sectorName}")
    public ResponseEntity<HttpStatus> isSectorNameAvailable(@PathVariable String sectorName) {
        if(sectorService.isSectorNameAvailable(sectorName)) {
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
        }
    }

    // Update Sector - {sectorId}
    @PutMapping("/sectors/update/{sectorId}")
    public ResponseEntity<Sector> updateSector(@PathVariable Long sectorId, @Valid @RequestBody Sector sector, BindingResult bindingResult) {
        Sector updatedSector = sectorService.updateSector(sectorId, sector);
        return new ResponseEntity<Sector>(updatedSector, null, HttpStatus.OK);
    }

    // Delete Sector - {sectorId}
    @DeleteMapping("/sectors/{sectorId}")
    public ResponseEntity<HttpStatus> removeSector(@PathVariable Long sectorId) {
        sectorService.removeSector(sectorId);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
}
