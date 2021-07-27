package com.stockmarket.company.service;

import com.stockmarket.company.entity.IPODetail;

import java.util.List;

public interface IIPODetailService {
    public List<IPODetail> listIPODetails();
    public IPODetail mapIpoExchange(String companyName, String exchangeName);
    public IPODetail newIPODetail(String companyName, IPODetail ipoDetail);
    public IPODetail getIPODetail(String exchangeName);
    public IPODetail updateIPODetail(String companyName, IPODetail ipoDetail);
    public List<IPODetail> listUpcoming();
    public void removeIPODetail(String exchangeName);

}
