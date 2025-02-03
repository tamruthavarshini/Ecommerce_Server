package com.example.EcommerceServer.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    // Define the base directory where the images are stored
    private final String CATEGORY_IMAGE_DIRECTORY_PATH = "src/main/resources/assets/snapcart_categories/";
    private final String PRODUCT_IMAGE_DIRECTORY_PATH="src/main/resources/assets/snapcart_products/";
    // Endpoint to fetch images by their name
    @GetMapping("/categories/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        try {
            // Construct the full path to the image
            Path imagePath = Paths.get(CATEGORY_IMAGE_DIRECTORY_PATH + imageName);

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
