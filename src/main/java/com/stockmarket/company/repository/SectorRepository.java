package com.stockmarket.company.repository;

import com.stockmarket.company.entity.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SectorRepository extends JpaRepository<Sector, Long> {
    public Optional<Sector> findByName(String sectorName);
}
