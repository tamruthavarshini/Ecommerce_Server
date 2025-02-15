package com.example.EcommerceServer.controllers;

import com.example.EcommerceServer.Dto.AddressDto;
import com.example.EcommerceServer.Dto.ResponseMessageDto;
import com.example.EcommerceServer.config.auth.TokenProvider;
import com.example.EcommerceServer.models.CartItem;
import com.example.EcommerceServer.models.order.Order;
import com.example.EcommerceServer.models.user.User;
import com.example.EcommerceServer.repository.CartItemRepository;
import com.example.EcommerceServer.repository.OrderRepository;
import com.example.EcommerceServer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private TokenProvider tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @PostMapping("/checkout")
    public ResponseEntity<ResponseMessageDto> checkout(
            @RequestHeader("Authorization") String token,
            @RequestBody AddressDto address) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
        String username = tokenService.decodeJWT(jwt);
        User user = userRepository.findByUsername(username)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body(new ResponseMessageDto("Invalid token or user."));
        }

        user.setAddress(address.address());
        userRepository.save(user);

        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        for (CartItem cartItem : cartItems) {
            Order order = new Order();
            order.setQuantity(cartItem.getQuantity());
            order.setVariant(cartItem.getVariant());
            order.setProduct(cartItem.getProduct());
            order.setUser(user);
            orderRepository.save(order);
        }

        cartItemRepository.deleteAll(cartItems);

        return ResponseEntity.ok(new ResponseMessageDto("Checkout successful and order placed."));
    }

    @GetMapping("/myorders")
    public ResponseEntity<List<Order>> getMyOrders(
            @RequestHeader("Authorization") String token) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
        String username = tokenService.decodeJWT(jwt);
        User user = userRepository.findByUsername(username)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }

        List<Order> orders = orderRepository.findByUserOrderByCreatedAtDesc(user);

        return ResponseEntity.ok(orders);
    }
}
