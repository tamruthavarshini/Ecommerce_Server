package com.example.EcommerceServer.models.user;


import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
//
//Table: cart_items
//Columns:
//id int AI PK
//user_id int
//product_id int
//product_image varchar(255)
//brand varchar(255)
//description text
//cost decimal(10,2)
//discount decimal(5,2)
//variant varchar(255)
//quantity int
//
//
//Table: wishlist_items
//Columns:
//id int AI PK
//user_id int
//product_id int
//product_image varchar(255)
//brand varchar(255)
//description text
//cost decimal(10,2)
//discount decimal(5,2)
//variant varchar(255)


//Table: users
//Columns:
//user_id int AI PK
//name varchar(255)
//phone_number varchar(255)
//address varchar(255)
//email varchar(255)
//gender varchar(255)
//Role varchar(255)
//username varchar(255)
//password varchar(255)
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String address, email,gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.Customer;
    private String username,password;

    public User(Long id, String name, String phoneNumber, String address, String email, String gender, Role role, String username, String password) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.gender = gender;
        this.role = role;
        this.username = username;
        this.password = password;
    }

    public User() {
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
