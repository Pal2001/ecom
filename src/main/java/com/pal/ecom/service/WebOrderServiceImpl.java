package com.pal.ecom.service;

import com.pal.ecom.model.LocalUser;
import com.pal.ecom.model.WebOrder;
import com.pal.ecom.model.dao.WebOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebOrderServiceImpl implements WebOrderService{

    @Autowired
    WebOrderDao webOrderDao;
    @Override
    public List<WebOrder> getOrders(LocalUser user) {
        return webOrderDao.findByUser(user);
    }
}
