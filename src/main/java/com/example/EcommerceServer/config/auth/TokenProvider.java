package com.example.EcommerceServer.config.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.EcommerceServer.models.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;



@Service
public class TokenProvider {
    private static final String JWT_SECRET = "it_is_a_secret_key";

    public String generateAccessToken(User user) {
        String role = user.getRole().name();
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.create()
                    .withSubject(user.getUsername())
                    .withClaim("role", role)
                    .withExpiresAt(genAccessExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Error while generating token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            var decodedJWT = JWT.decode(token);

            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
//            System.out.println(" Token verification failed: " + exception.getMessage());
            throw new JWTVerificationException("Error while validating token", exception);
        }
    }


    private Instant genAccessExpirationDate() {
        return LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.UTC);
    }

    public String decodeJWT(String jwtToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.require(algorithm)
                    .build()
                    .verify(jwtToken)
                    .getSubject();  // Extracts username
        } catch (JWTVerificationException e) {
            System.err.println("Invalid JWT Token: " + e.getMessage());
            return null;
        }
    }


}