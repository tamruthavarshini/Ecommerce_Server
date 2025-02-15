package com.example.EcommerceServer.controllers;

import com.example.EcommerceServer.models.Product;
import com.example.EcommerceServer.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
    @Autowired
    private ProductsRepository productsRepository;

    @GetMapping("/{categoryId}/{page}/{size}")
    public Page<Product> getAllProducts(@PathVariable int categoryId, @PathVariable int page, @PathVariable int size) {
        Page<Product> products = productsRepository.findByCategoryId(Long.valueOf(categoryId), PageRequest.of(page, size));
        for (Product product : products) {
            product.setImage1("http://localhost:8080/api/images/products/" + product.getImage1() + ".png");
        }
        return products;
    }

    @GetMapping("/{categoryId}/{productId}")
    public Product getProductDetails(@PathVariable int categoryId, @PathVariable int productId) {
        Product product = productsRepository.findByProductId(productId);
        product.setImage1("http://localhost:8080/api/images/products/" + product.getImage1() + ".png");
        product.setImage2("http://localhost:8080/api/images/products/" + product.getImage2() + ".png");
        product.setImage3("http://localhost:8080/api/images/products/" + product.getImage3() + ".png");
        product.setImage4("http://localhost:8080/api/images/products/" + product.getImage4() + ".png");
        product.setImage5("http://localhost:8080/api/images/products/" + product.getImage5() + ".png");

        return product;
    }


    @GetMapping("/random")
    public List<Product> getRandomProducts() {
        List<Product> products = productsRepository.findRandomProducts(5);
        for (Product product : products) {
            product.setImage1("http://localhost:8080/api/images/products/" + product.getImage1() + ".png");
        }
        return products;
    }


    @GetMapping("/similar/{categoryId}/{productId}")
    public ResponseEntity<List<Product>> getSimilarProducts(
            @PathVariable Long categoryId,
            @PathVariable Long productId) {
        Pageable limit = PageRequest.of(0, 5);
        List<Product> similarProducts = productsRepository.findSimilarProducts(categoryId, productId, limit);
        for (Product product : similarProducts) {
            product.setImage1("http://localhost:8080/api/images/products/" + product.getImage1() + ".png");
        }
        return ResponseEntity.ok(similarProducts);
    }

    @GetMapping("/product/{productId}")
    public Product getProductDetails(@PathVariable int productId) {
        Product product = productsRepository.findByProductId(productId);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        product.setImage1("http://localhost:8080/api/images/products/" + product.getImage1() + ".png");
        product.setImage2("http://localhost:8080/api/images/products/" + product.getImage2() + ".png");
        product.setImage3("http://localhost:8080/api/images/products/" + product.getImage3() + ".png");
        product.setImage4("http://localhost:8080/api/images/products/" + product.getImage4() + ".png");
        product.setImage5("http://localhost:8080/api/images/products/" + product.getImage5() + ".png");

        return product;
    }

}