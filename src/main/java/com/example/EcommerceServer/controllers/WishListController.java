package com.example.EcommerceServer.controllers;

import com.example.EcommerceServer.Dto.ResponseMessageDto;
import com.example.EcommerceServer.Dto.WishListRequestDto;
import com.example.EcommerceServer.config.auth.TokenProvider;
import com.example.EcommerceServer.models.Category;
import com.example.EcommerceServer.models.Product;
import com.example.EcommerceServer.models.WishList;
import com.example.EcommerceServer.models.user.User;
import com.example.EcommerceServer.repository.ProductsRepository;
import com.example.EcommerceServer.repository.UserRepository;
import com.example.EcommerceServer.repository.WishListRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


//@CrossOrigin(origins = {"http://127.0.0.1:5500"}, allowCredentials = "true")
@RestController
@RequestMapping("/api/wishlist")
public class WishListController {

    @Autowired
    private TokenProvider tokenService;

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductsRepository productRepository;

    @PostMapping("/add")
    public ResponseEntity<ResponseMessageDto> addProductToWishList(
            @RequestHeader("Authorization") String token,
            @RequestBody WishListRequestDto requestDto) {
        System.out.println("token"+token);
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
        System.out.println(jwt);
        String username = (tokenService.decodeJWT(jwt));
        System.out.println("username"+username);
        User user = userRepository.findByUsername(username)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body(new ResponseMessageDto("Invalid token or user."));
        }
        Product product = productRepository.findById(requestDto.productId()).orElse(null);

        if (product == null) {
            return ResponseEntity.badRequest().body(new ResponseMessageDto("Invalid product ID."));
        }

        WishList wishList = new WishList();
        wishList.setUserId(user);
        wishList.setProductId(product);
        wishListRepository.save(wishList);

        return ResponseEntity.ok(new ResponseMessageDto("Product added to wishlist successfully."));
    }

    @Transactional
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ResponseMessageDto> deleteProductFromWishList(
            @RequestHeader("Authorization") String token,
            @PathVariable Long productId) {
        System.out.println("token" + token);
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
        String username = tokenService.decodeJWT(jwt);
        System.out.println("username" + username);
        User user = userRepository.findByUsername(username)
                .orElse(null);
        System.out.println("user" + user);
        if (user == null) {
            return ResponseEntity.badRequest().body(new ResponseMessageDto("Invalid token or user."));
        }

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return ResponseEntity.badRequest().body(new ResponseMessageDto("Invalid product ID."));
        }

        wishListRepository.deleteByUserIdAndProductId(user, product);
        return ResponseEntity.ok(new ResponseMessageDto("Product deleted from wishlist successfully."));
    }

    @GetMapping("/get")
    public ResponseEntity<List<WishList>> getWishList(@RequestHeader("Authorization") String token) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
        String username = tokenService.decodeJWT(jwt);
        User user = userRepository.findByUsername(username)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }

        List<WishList> wishList = wishListRepository.findByUserId(user);
        for (WishList wishlist : wishList) {
            wishlist.getProductId().setImage1("http://localhost:8080/api/images/products/" + wishlist.getProductId().getImage1() + ".png");
        }
        return ResponseEntity.ok(wishList);
    }
}