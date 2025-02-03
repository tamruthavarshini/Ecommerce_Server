package com.example.EcommerceServer.repository;

import com.example.EcommerceServer.models.categories;
import com.example.EcommerceServer.models.products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepository  extends JpaRepository<products,Long> {
    List<products> findByCategoryId(int categoryId);
    products findByProductId(int productId);
}
