package com.example.EcommerceServer.repository;

import com.example.EcommerceServer.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriesRepository  extends JpaRepository<Category,Long> {

    Optional<Category> findByName(String categoryName);
}
