package com.example.EcommerceServer.controllers;


import com.example.EcommerceServer.models.categories;
import com.example.EcommerceServer.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin("*")
public class CategoriesController {
    @Autowired
    private CategoriesRepository categoriesRepository;

    @GetMapping
    public List<categories> getAllCategories() {
        List<categories> categories =  categoriesRepository.findAll();
        for (categories category : categories) {
            category.setImage("http://localhost:8080/api/images/categories/" + category.getImage() + ".png");
        }
        return categories;
    }
}
