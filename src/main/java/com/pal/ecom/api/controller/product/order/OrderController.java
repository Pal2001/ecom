package com.pal.ecom.api.controller.product.order;

import com.pal.ecom.model.LocalUser;
import com.pal.ecom.model.WebOrder;
import com.pal.ecom.service.WebOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    WebOrderService webOrderService;
    @GetMapping
    public List<WebOrder> getOrders(@AuthenticationPrincipal LocalUser user){
        return webOrderService.getOrders(user);
    }
}
