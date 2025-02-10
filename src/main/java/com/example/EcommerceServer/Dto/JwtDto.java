package com.example.EcommerceServer.Dto;

public record JwtDto (String accessToken, String role, String username,String name,String email,String phone,String address) {
}
