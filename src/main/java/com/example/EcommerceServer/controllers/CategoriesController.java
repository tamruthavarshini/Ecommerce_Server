package com.example.EcommerceServer.controllers;


import com.example.EcommerceServer.models.Category;
import com.example.EcommerceServer.repository.CategoriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {
    @Autowired
    private CategoriesRepository categoriesRepository;

    private static final Logger logger = LoggerFactory.getLogger(CategoriesController.class);

    @GetMapping
    public List<Category> getAllCategories() {
        long startTime = System.currentTimeMillis();
        List<Category> categories =  categoriesRepository.findAll();
        for (Category category : categories) {
            category.setImage("http://localhost:8080/api/images/categories/" + category.getImage() + ".png");
        }
        long endTime = System.currentTimeMillis(); // Capture end time
        logger.info("Response time for /categories: {} ms", (endTime - startTime));
        return categories;
    }
}
