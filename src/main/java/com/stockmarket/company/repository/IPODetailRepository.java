package com.stockmarket.company.repository;

import com.stockmarket.company.entity.IPODetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface IPODetailRepository extends JpaRepository<IPODetail, Long> {
    List<IPODetail> findByOpenDateTime(LocalDateTime from);
}
