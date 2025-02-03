package com.example.EcommerceServer.controllers;

import com.example.EcommerceServer.models.Product;
import com.example.EcommerceServer.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
    @Autowired
    private ProductsRepository productsRepository;

//    @Autowired
//    private WishListRepository wishListRepository;
        @GetMapping("/{categoryId}")
        public List<Product> getAllProducts(@PathVariable int categoryId)
        {
            List<Product> products =  productsRepository.findByCategoryId(categoryId);
            for (Product product : products) {
                product.setImage1("http://localhost:8080/api/images/products/" + product.getImage1() + ".png");
                product.setImage2("http://localhost:8080/api/images/products/" + product.getImage2() + ".png");
                product.setImage3("http://localhost:8080/api/images/products/" + product.getImage3() + ".png");
                product.setImage4("http://localhost:8080/api/images/products/" + product.getImage4() + ".png");
                product.setImage5("http://localhost:8080/api/images/products/" + product.getImage5() + ".png");
            }
            return products;
        }

        @GetMapping("/{categoryId}/{productId}")
        public Product getProductDetails(@PathVariable int categoryId,@PathVariable int productId)
        {
            Product product = productsRepository.findByProductId(productId);

            return product;
        }
//
//        @PostMapping("/addtowishlist")
//        public void addToWishList(@RequestBody int productId)
//        {
//            Product product = productsRepository.findByProductId(productId);
//
//        }


}
