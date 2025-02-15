package com.example.EcommerceServer.repository;

import com.example.EcommerceServer.models.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductsRepository  extends JpaRepository<Product,Long> {
    List<Product> findByCategoryId(int categoryId);
    Product findByProductId(int productId);
    List<Product> findByBrandContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String brand, String description);
    //List<Product> findByMerchantId(Long merchantId);
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
        @Query(value = "SELECT * FROM Products ORDER BY RAND() LIMIT :limit", nativeQuery = true)
        List<Product> findRandomProducts(@Param("limit") int limit);
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.productId != :productId ORDER BY FUNCTION('RAND')")
    List<Product> findSimilarProducts(@Param("categoryId") Long categoryId, @Param("productId") Long productId, Pageable limit);

    Page<Product> findByMerchantId(Long merchantId, Pageable pageable);

//List<Product> findByCategoryIdAndProductIdNot(Long categoryId, Long productId);
}
