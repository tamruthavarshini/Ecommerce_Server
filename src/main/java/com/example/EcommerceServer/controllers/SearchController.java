package com.example.EcommerceServer.controllers;

import com.example.EcommerceServer.models.Product;

import com.example.EcommerceServer.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private  ProductsRepository productRepository;

    @GetMapping("/{keyword}")
    public List<Product> searchProducts(@PathVariable String keyword) {
        List<Product> products= productRepository.findByBrandContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
        for (Product product : products) {
            product.setImage1("http://localhost:8080/api/images/products/" + product.getImage1() + ".png");
        }
        return products;
    }
}
