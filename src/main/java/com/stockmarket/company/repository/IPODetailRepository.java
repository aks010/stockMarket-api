package com.stockmarket.company.repository;

import com.stockmarket.company.entity.IPODetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPODetailRepository extends JpaRepository<IPODetail, Long> {
}
