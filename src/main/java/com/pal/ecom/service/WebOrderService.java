package com.pal.ecom.service;

import com.pal.ecom.model.LocalUser;
import com.pal.ecom.model.WebOrder;

import java.util.List;

public interface WebOrderService {
    List<WebOrder> getOrders(LocalUser user);
}
