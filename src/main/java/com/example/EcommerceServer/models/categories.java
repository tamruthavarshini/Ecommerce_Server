package com.example.EcommerceServer.models;
import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class categories {
    @Id
    private Long id;
    private String name;
    private byte[] image;
    public categories(Long id, String name, byte[] image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
