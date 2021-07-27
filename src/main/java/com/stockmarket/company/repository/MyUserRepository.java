package com.stockmarket.company.repository;

import com.stockmarket.company.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface MyUserRepository extends JpaRepository<MyUser, Long>, PagingAndSortingRepository<MyUser, Long> {
    public Optional<MyUser> findByEmail(String email);

    public Optional<MyUser> findByUsername(String username);
}
