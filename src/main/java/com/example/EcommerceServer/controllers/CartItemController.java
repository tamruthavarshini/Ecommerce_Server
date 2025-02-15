package com.example.EcommerceServer.controllers;

import com.example.EcommerceServer.Dto.CartRequestDto;
import com.example.EcommerceServer.Dto.ResponseMessageDto;
import com.example.EcommerceServer.Dto.WishListRequestDto;
import com.example.EcommerceServer.config.auth.TokenProvider;
import com.example.EcommerceServer.models.CartItem;
import com.example.EcommerceServer.models.Product;
import com.example.EcommerceServer.models.WishList;
import com.example.EcommerceServer.models.user.User;
import com.example.EcommerceServer.repository.CartItemRepository;
import com.example.EcommerceServer.repository.ProductsRepository;
import com.example.EcommerceServer.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/cartitems")
public class CartItemController {

    @Autowired
    private TokenProvider tokenService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductsRepository productRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @PostMapping("/add")
    public ResponseEntity<ResponseMessageDto> addProductToCartItem(
            @RequestHeader("Authorization") String token,
            @RequestBody CartRequestDto requestDto) {
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
        CartItem cartItem1 = cartItemRepository.findByProductAndUser(product, user);
        System.out.println("cartItem1"+cartItem1);
        if(cartItem1!=null)
        {
            return ResponseEntity.ok(new ResponseMessageDto("Product added to cart successfully."));
        }

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(requestDto.quantity());
        cartItem.setUser(user);
        cartItem.setVariant(requestDto.variant());
        cartItemRepository.save(cartItem);

        return ResponseEntity.ok(new ResponseMessageDto("Product added to cart successfully."));
    }

    @PutMapping("/updateQuantity")
    public ResponseEntity<ResponseMessageDto> updateQuantity(
            @RequestHeader("Authorization") String token,
            @RequestBody CartRequestDto requestDto) {
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

        CartItem cartItem = cartItemRepository.findByProductAndUser(product, user);
        if (cartItem == null) {
            return ResponseEntity.badRequest().body(new ResponseMessageDto("Product not found in cart."));
        }
        cartItem.setQuantity(requestDto.quantity());
        cartItemRepository.save(cartItem);

        return ResponseEntity.ok(new ResponseMessageDto("Product quantity updated successfully."));
    }

    @PutMapping("/updateVariant")
    public ResponseEntity<ResponseMessageDto> updateVariant(
            @RequestHeader("Authorization") String token,
            @RequestBody CartRequestDto requestDto) {
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

        CartItem cartItem = cartItemRepository.findByProductAndUser(product, user);
        if (cartItem == null) {
            return ResponseEntity.badRequest().body(new ResponseMessageDto("Product not found in cart."));
        }
        cartItem.setVariant(requestDto.variant());
        cartItemRepository.save(cartItem);

        return ResponseEntity.ok(new ResponseMessageDto("Product variant updated successfully."));
    }

    @Transactional
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ResponseMessageDto> deleteProductFromCart(
            @RequestHeader("Authorization") String token,
            @PathVariable Long productId) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
        String username = tokenService.decodeJWT(jwt);
        User user = userRepository.findByUsername(username)
                .orElse(null);
System.out.println("deleet product from cart"+user);
        if (user == null) {
            return ResponseEntity.badRequest().body(new ResponseMessageDto("Invalid token or user."));
        }

        Product product = productRepository.findById(productId).orElse(null);
        System.out.println("deleet product from cart"+product);
        if (product == null) {
            return ResponseEntity.badRequest().body(new ResponseMessageDto("Invalid product ID."));
        }

        CartItem cartItem = cartItemRepository.findByProductAndUser(product, user);
        if (cartItem == null) {
            return ResponseEntity.badRequest().body(new ResponseMessageDto("Product not found in cart."));
        }

        cartItemRepository.delete(cartItem);
        return ResponseEntity.ok(new ResponseMessageDto("Product deleted from cart successfully."));
    }

    @GetMapping("/get")
    public ResponseEntity<List<CartItem>> getCartItems(@RequestHeader("Authorization") String token) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
        String username = tokenService.decodeJWT(jwt);
        User user = userRepository.findByUsername(username)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }

        List<CartItem> cartItem = cartItemRepository.findByUser(user);
        for (CartItem cartitem : cartItem) {
            //cartitem.getProduct().setImage1("http://localhost:8080/api/images/products/" + cartitem.getProduct().getImage1() + ".png");
System.out.println(cartitem.getProduct().getImage1());
        }
        return ResponseEntity.ok(cartItem);
    }
    @GetMapping("/check")
    public ResponseEntity<ResponseMessageDto> checkProductInCart(
            @RequestHeader("Authorization") String token,
            @RequestParam Long productId,
            @RequestParam String variant) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
        String username = tokenService.decodeJWT(jwt);
        User user = userRepository.findByUsername(username)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body(new ResponseMessageDto("Invalid token or user."));
        }

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return ResponseEntity.badRequest().body(new ResponseMessageDto("Invalid product ID."));
        }

        CartItem cartItem = cartItemRepository.findByProductAndUserAndVariant(product, user, variant);
        if (cartItem != null) {
            return ResponseEntity.ok(new ResponseMessageDto("Yes"));
        } else {
            return ResponseEntity.ok(new ResponseMessageDto("No"));
        }
    }
}
