package com.stockmarket.company.service;

import com.stockmarket.company.entity.MyUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMyUserService {
    public Page<MyUser> listUsers(Pageable pageable);
    public MyUser newUser(MyUser stockExchange);
    public MyUser loginMyUser(MyUser myUser);
    public MyUser getUser(Long stockId);
    public MyUser updateUser(Long stockId, MyUser stockExchange);
    public void removeUser(Long stockId);
    public String confirmUser(String email);
}
