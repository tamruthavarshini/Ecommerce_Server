package com.example.EcommerceServer.repository;

import com.example.EcommerceServer.models.Product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepository  extends JpaRepository<Product,Long> {
    List<Product> findByCategoryId(int categoryId);
    Product findByProductId(int productId);
}
