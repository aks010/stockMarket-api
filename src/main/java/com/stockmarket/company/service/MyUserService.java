package com.stockmarket.company.service;

import com.stockmarket.company.entity.MyUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MyUserService {
    public Page<MyUser> listUsers(Pageable pageable);
    public MyUser getUser(String username);
    public MyUser updateUser(String username, MyUser stockExchange);
    public void removeUser(Long stockId);
    public String confirmUser(String email);
}
