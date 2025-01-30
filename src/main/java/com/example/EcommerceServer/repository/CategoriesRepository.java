package com.example.EcommerceServer.repository;

import com.example.EcommerceServer.models.categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository  extends JpaRepository<categories,Long> {

}
