package com.example.EcommerceServer.models;

import com.example.EcommerceServer.models.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Foreign Key -> users(user_id)

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // Foreign Key -> products(product_id)

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = true)
    private String variant; // Can be null if the product has no variants

    public CartItem() {
    }

    public CartItem(Long id, User user, Product product, int quantity, String variant) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.variant = variant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }
}