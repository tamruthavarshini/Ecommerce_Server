package com.example.EcommerceServer.repository;

import com.example.EcommerceServer.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository  extends JpaRepository<Category,Long> {

}
