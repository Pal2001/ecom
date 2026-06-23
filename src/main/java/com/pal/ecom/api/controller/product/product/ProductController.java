package com.pal.ecom.api.controller.product.product;

import com.pal.ecom.model.Product;
import com.pal.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @GetMapping
    public List<Product> getProducts(){
        return productService.getProducts();
    }
}
