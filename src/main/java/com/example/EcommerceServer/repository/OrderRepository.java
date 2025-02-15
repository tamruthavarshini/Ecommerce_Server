package com.example.EcommerceServer.repository;

import com.example.EcommerceServer.models.order.Order;
import com.example.EcommerceServer.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
    List<Order> findByUserOrderByCreatedAtDesc(@Param("user") User user);
    List<Order> findByProduct_MerchantId(Long merchantId);
    Optional<Order> findById(Long id);
}
