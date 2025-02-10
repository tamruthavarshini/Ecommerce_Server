package com.example.EcommerceServer.repository;


import com.example.EcommerceServer.models.Product;
import com.example.EcommerceServer.models.WishList;
import com.example.EcommerceServer.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList,Long> {
    void deleteByUserIdAndProductId(User user, Product productId);

    List<WishList> findByUserId(User user);
    //WishList findByUserId(int userId);
    //WishList findByWishListId(int wishListId);

}
