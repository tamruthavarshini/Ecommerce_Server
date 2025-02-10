package com.example.EcommerceServer.controllers;


import com.example.EcommerceServer.Dto.JwtDto;
import com.example.EcommerceServer.Dto.LoginRequest;
import com.example.EcommerceServer.Dto.ResponseMessageDto;
import com.example.EcommerceServer.Dto.SignupRequest;
import com.example.EcommerceServer.config.auth.TokenProvider;
import com.example.EcommerceServer.models.user.User;
import com.example.EcommerceServer.repository.UserRepository;
import jakarta.validation.Valid;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class UsersController {
    Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private TokenProvider tokenService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public UsersController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseMessageDto> registerUser(@RequestBody @Validated SignupRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            return ResponseEntity.badRequest().body(new ResponseMessageDto("Email is already in use."));
        }
        if (userRepository.existsByUsername(request.username())) {
            return ResponseEntity.badRequest().body(new ResponseMessageDto("Username is already taken."));
        }

        User user = new User();
        user.setName(request.name());
        user.setPhoneNumber(request.phone());
        user.setAddress(request.address());
        user.setEmail(request.email());
        user.setGender(request.gender());
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);

        return ResponseEntity.ok(new ResponseMessageDto("User registered successfully."));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtDto> signIn(@RequestBody @Valid LoginRequest data) {
        logger.info("this is auth controller");
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        logger.info(String.valueOf(usernamePassword));
        Authentication authUser = authenticationManager.authenticate((Authentication) usernamePassword);
        logger.info(String.valueOf(authUser));
        User user = (User) authUser.getPrincipal();
        var accessToken = tokenService.generateAccessToken(user);
        String role = user.getRole().name();

        logger.info("Authentication successful for user: " + user.getId());

        String username = user.getUsername();
        String name = user.getName();
        String email = user.getEmail();
        String phoneNumber = user.getPhoneNumber();
        String address = user.getAddress();


        return ResponseEntity.ok(new JwtDto(accessToken, role,username, name,email,phoneNumber,address));
    }

}
