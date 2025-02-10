package com.example.EcommerceServer.repository;

import com.example.EcommerceServer.models.CartItem;
import com.example.EcommerceServer.models.Product;
import com.example.EcommerceServer.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    CartItem findByProductAndUser(Product product, User user);

    List<CartItem> findByUser(User user);
}
