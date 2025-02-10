package com.example.EcommerceServer.models;

import com.example.EcommerceServer.models.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "wishlist_items")
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product productId;

    public WishList() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }



    public WishList(int id, User userId, Product productId, String productImage, String brand, String description, String variant, float cost, float discount) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;

    }
}