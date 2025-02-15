package com.example.EcommerceServer.controllers;


import com.example.EcommerceServer.Dto.OrderStatusRequestDto;
import com.example.EcommerceServer.Dto.ResponseMessageDto;
import com.example.EcommerceServer.config.auth.TokenProvider;
import com.example.EcommerceServer.models.Category;
import com.example.EcommerceServer.models.Product;
import com.example.EcommerceServer.models.order.Order;
import com.example.EcommerceServer.models.order.OrderStatus;
import com.example.EcommerceServer.models.user.User;
import com.example.EcommerceServer.repository.CategoriesRepository;
import com.example.EcommerceServer.repository.OrderRepository;
import com.example.EcommerceServer.repository.ProductsRepository;
import com.example.EcommerceServer.repository.UserRepository;
import com.example.EcommerceServer.services.ImageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/merchant")
public class MerchantController {
    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private ProductsRepository productRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private TokenProvider tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

//    @PostMapping("/addProduct")
//    public ResponseEntity<ResponseMessageDto> uploadMultipleImages(
//            @RequestHeader("Authorization") String token,
//            @RequestParam("category_name") String categoryName,
//            @RequestParam("brand") String brand,
//            @RequestParam("description") String description,
//            @RequestParam("cost") float cost,
//            @RequestParam("discount") float discount,
//            @RequestParam("variants") String variants,
//            @RequestParam("images") MultipartFile[] files) {
//        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
//        String username = tokenService.decodeJWT(jwt);
//        User user = userRepository.findByUsername(username)
//                .orElse(null);
//
//        if (user == null) {
//            return ResponseEntity.badRequest().body(new ResponseMessageDto("Invalid token or user."));
//        }
//
//        try {
//
//            Category category = categoriesRepository.findByName(categoryName)
//                    .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryName));
//
//            System.out.println("Received request to add product...");
//
//            System.out.println("Uploading images...");
//            List<String> filenames = imageService.uploadImages(files);
//
//            System.out.println("Creating product entity...");
//            Product product = new Product();
//            product.setCategory(category); // Set category object
//            product.setBrand(brand);
//            product.setDescription(description);
//            product.setCost(cost);
//            product.setDiscount(discount);
//            product.setVariants(variants);
//            product.setMerchantId(user.getId());
//
//            if (!filenames.isEmpty()) {
//                product.setImage1(filenames.size() > 0 ? filenames.get(0) : null);
//                product.setImage2(filenames.size() > 1 ? filenames.get(1) : null);
//                product.setImage3(filenames.size() > 2 ? filenames.get(2) : null);
//                product.setImage4(filenames.size() > 3 ? filenames.get(3) : null);
//                product.setImage5(filenames.size() > 4 ? filenames.get(4) : null);
//            }
//
//            System.out.println("Saving product to database...");
//            productRepository.save(product);
//
//            System.out.println("Product added successfully!");
//            return ResponseEntity.ok(new ResponseMessageDto("Product added successfully!"));
//
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(new ResponseMessageDto("Error: " + e.getMessage()));
//        } catch (IOException e) {
//            return ResponseEntity.status(500).body(new ResponseMessageDto("Image upload failed: " + e.getMessage()));
//        }
//    }
@PostMapping("/addProduct")
public ResponseEntity<ResponseMessageDto> uploadMultipleImages(
        @RequestHeader("Authorization") String token,
        @RequestParam("category_name") String categoryName,
        @RequestParam("brand") String brand,
        @RequestParam("description") String description,
        @RequestParam("cost") float cost,
        @RequestParam("discount") float discount,
        @RequestParam("variants") String variants,
        @RequestParam("images") MultipartFile[] files) {

    long startTime = System.currentTimeMillis(); // Start time

    String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
    String username = tokenService.decodeJWT(jwt);
    User user = userRepository.findByUsername(username)
            .orElse(null);

    if (user == null) {
        return ResponseEntity.badRequest().body(new ResponseMessageDto("Invalid token or user."));
    }

    try {
        Category category = categoriesRepository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryName));

        List<String> filenames = imageService.uploadImages(files);

        Product product = new Product();
        product.setCategory(category);
        product.setBrand(brand);
        product.setDescription(description);
        product.setCost(cost);
        product.setDiscount(discount);
        product.setVariants(variants);
        product.setMerchantId(user.getId());

        if (!filenames.isEmpty()) {
            product.setImage1(filenames.size() > 0 ? filenames.get(0) : null);
            product.setImage2(filenames.size() > 1 ? filenames.get(1) : null);
            product.setImage3(filenames.size() > 2 ? filenames.get(2) : null);
            product.setImage4(filenames.size() > 3 ? filenames.get(3) : null);
            product.setImage5(filenames.size() > 4 ? filenames.get(4) : null);
        }

        productRepository.save(product);

        long endTime = System.currentTimeMillis(); // End time
        long responseTime = endTime - startTime; // Calculate response time
System.out.println(responseTime);
        return ResponseEntity.ok(new ResponseMessageDto("Product added successfully! Response time: " + responseTime + "ms"));

    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(new ResponseMessageDto("Error: " + e.getMessage()));
    } catch (IOException e) {
        return ResponseEntity.status(500).body(new ResponseMessageDto("Image upload failed: " + e.getMessage()));
    }
}

//

    @GetMapping("/vieworders")
    public ResponseEntity<List<Order>> viewOrders(
            @RequestHeader("Authorization") String token) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
        String username = tokenService.decodeJWT(jwt);
        User user = userRepository.findByUsername(username)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }

        List<Order> orders = orderRepository.findByProduct_MerchantId(user.getId());
        for (Order order : orders) {

            order.getProduct().setImage1("http://localhost:8080/api/images/products/" + order.getProduct().getImage1() + ".png");
        }
        return ResponseEntity.ok(orders);
    }


    @GetMapping("/customerdetails")
    public ResponseEntity<User> getUserDetails(
            @RequestHeader("Authorization") String token,
            @RequestParam("userId") Long userId) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
        String username = tokenService.decodeJWT(jwt);
        User merchant = userRepository.findByUsername(username)
                .orElse(null);

        if (merchant == null) {
            return ResponseEntity.badRequest().body(null);
        }

        User user = userRepository.findById(userId)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/updateorderstatus")
    public ResponseEntity<ResponseMessageDto> updateOrderStatus(
            @RequestHeader("Authorization") String token,
            @RequestBody OrderStatusRequestDto request) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
        String username = tokenService.decodeJWT(jwt);
        User merchant = userRepository.findByUsername(username)
                .orElse(null);

        if (merchant == null) {
            return ResponseEntity.badRequest().body(new ResponseMessageDto("Invalid token or user."));
        }

        int orderId = request.orderId();
        String status = request.status();
        System.out.println(status);

        Order order = orderRepository.findById((long)orderId)
                .orElse(null);


        if (order == null || order.getProduct().getMerchantId() != merchant.getId()) {
            return ResponseEntity.badRequest().body(new ResponseMessageDto("Order not found or does not belong to the merchant."));
        }
        System.out.println(order.getProduct().getMerchantId()+" "+ merchant.getId());
        OrderStatus orderStatus;
        try {
            orderStatus = OrderStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseMessageDto("Invalid order status."));
        }

        order.setStatus(orderStatus);
        orderRepository.save(order);

        return ResponseEntity.ok(new ResponseMessageDto("Order status updated successfully."));
    }


        @GetMapping("/products")
        public ResponseEntity<Page<Product>> getMerchantProducts(
                @RequestHeader("Authorization") String token,
                @RequestParam int page,
                @RequestParam int size) {
            String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
            String username = tokenService.decodeJWT(jwt);
            User user = userRepository.findByUsername(username)
                    .orElse(null);

            if (user == null) {
                return ResponseEntity.badRequest().body(null);
            }

            Pageable pageable = PageRequest.of(page, size);
            Page<Product> productsPage = productRepository.findByMerchantId( user.getId(),pageable);
            productsPage.forEach(product -> product.setImage1("http://localhost:8080/api/images/products/" + product.getImage1() + ".png"));
            return ResponseEntity.ok(productsPage);
        }


        @Transactional
        @DeleteMapping("/deleteproduct")
        public ResponseEntity<ResponseMessageDto> deleteProduct(
                @RequestHeader("Authorization") String token,
                @RequestParam Long productId) {
            String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
            String username = tokenService.decodeJWT(jwt);
            User user = userRepository.findByUsername(username)
                    .orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessageDto("Invalid token or user."));
            }

            Product product = productRepository.findById(productId)
                    .orElse(null);

            if (product == null || (product.getMerchantId()!=(user.getId()))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessageDto("Product not found or does not belong to the merchant."));
            }

            productRepository.delete(product);
            return ResponseEntity.ok(new ResponseMessageDto("Product deleted successfully."));
        }

    @PostMapping("/updateproduct")
    public ResponseEntity<String> updateProduct(@RequestBody Product updatedProduct, @RequestHeader("Authorization") String token) {
        // Extract token validation logic (Assuming you handle JWT authentication)
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: No token provided");
        }

        // Fetch existing product from DB
        Product existingProduct = productRepository.findByProductId(updatedProduct.getProductId());
        if (existingProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        // Update product details
        existingProduct.setBrand(updatedProduct.getBrand());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setCost(updatedProduct.getCost());
        existingProduct.setDiscount(updatedProduct.getDiscount());

        // Fetch and set category if provided
        if (updatedProduct.getCategory() != null && updatedProduct.getCategory().getId() != null) {
            Category category = categoriesRepository.findById(updatedProduct.getCategory().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
            existingProduct.setCategory(category);
        }

        // Save updated product
        productRepository.save(existingProduct);
        return ResponseEntity.ok("Product updated successfully");
    }
    }









