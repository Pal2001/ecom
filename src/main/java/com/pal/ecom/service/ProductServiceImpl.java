package com.pal.ecom.service;

import com.pal.ecom.model.Product;
import com.pal.ecom.model.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductDao productDao;
    @Override
    public List<Product> getProducts() {
        return productDao.findAll();
    }
}
