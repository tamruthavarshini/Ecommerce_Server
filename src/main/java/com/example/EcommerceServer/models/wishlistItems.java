package com.example.EcommerceServer.models;

import jakarta.persistence.*;

@Entity
@Table(name = "wishlist_items")
public class wishlistItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private users userId;

    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private products productId;

    @Column(name="product_image")
    private String productImage;
    private String brand,description,variant;
    private float cost,discount;

    public wishlistItems() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public users getUserId() {
        return userId;
    }

    public void setUserId(users userId) {
        this.userId = userId;
    }

    public products getProductId() {
        return productId;
    }

    public void setProductId(products productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public wishlistItems(int id, users userId, products productId, String productImage, String brand, String description, String variant, float cost, float discount) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.productImage = productImage;
        this.brand = brand;
        this.description = description;
        this.variant = variant;
        this.cost = cost;
        this.discount = discount;
    }
}
