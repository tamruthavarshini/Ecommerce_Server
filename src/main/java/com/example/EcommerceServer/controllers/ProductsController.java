package com.example.EcommerceServer.controllers;

import com.example.EcommerceServer.models.categories;
import com.example.EcommerceServer.models.products;
import com.example.EcommerceServer.repository.ProductsRepository;
import com.example.EcommerceServer.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private WishListRepository wishListRepository;
        @GetMapping("/{categoryId}")
        public List<products> getAllProducts(@PathVariable int categoryId)
        {
            List<products> products =  productsRepository.findByCategoryId(categoryId);
            for (products product : products) {
                product.setImage1("http://localhost:8080/api/images/products/" + product.getImage1() + ".png");
                product.setImage2("http://localhost:8080/api/images/products/" + product.getImage2() + ".png");
                product.setImage3("http://localhost:8080/api/images/products/" + product.getImage3() + ".png");
                product.setImage4("http://localhost:8080/api/images/products/" + product.getImage4() + ".png");
                product.setImage5("http://localhost:8080/api/images/products/" + product.getImage5() + ".png");
            }
            return products;
        }

        @GetMapping("/{categoryId}/{productId}")
        public products getProductDetails(@PathVariable int categoryId,@PathVariable int productId)
        {
            products product = productsRepository.findByProductId(productId);

            return product;
        }

        @PostMapping("/addtowishlist")
        public void addToWishList(@RequestBody int productId)
        {
            products product = productsRepository.findByProductId(productId);

        }


}
