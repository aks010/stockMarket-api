package com.stockmarket.company.repository;

import com.stockmarket.company.entity.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectorRepository extends JpaRepository<Sector, Long> {
}
