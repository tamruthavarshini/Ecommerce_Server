package com.example.EcommerceServer.controllers;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    private final String CATEGORY_IMAGE_DIRECTORY_PATH = "src/main/resources/assets/snapcart_categories/";
    private final String PRODUCT_IMAGE_DIRECTORY_PATH="src/main/resources/assets/snapcart_products/";
    @GetMapping("/categories/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        try {
            // Construct the full path to the image
            Path imagePath = Paths.get(CATEGORY_IMAGE_DIRECTORY_PATH + imageName);
            System.out.println("image"+imagePath);
            if (imagePath.toFile().exists()) {
                Resource resource = new FileSystemResource(imagePath);
                return ResponseEntity.ok(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }


    @GetMapping("/products/{imageName}")
    public ResponseEntity<Resource> getProductsImage(@PathVariable String imageName) {
        try {
            // Construct the full path to the image
            Path imagePath = Paths.get(PRODUCT_IMAGE_DIRECTORY_PATH + imageName);

            // Check if file exists
            if (imagePath.toFile().exists()) {
                Resource resource = new FileSystemResource(imagePath);
                return ResponseEntity.ok(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }


}
