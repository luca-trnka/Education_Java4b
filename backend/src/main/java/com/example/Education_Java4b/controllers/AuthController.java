package com.example.Education_Java4b.controllers;

import com.example.Education_Java4b.config.jwt.JwtTokenProvider;
import com.example.Education_Java4b.dtos.UserDTO;
import com.example.Education_Java4b.models.User;
import com.example.Education_Java4b.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserDTO loginUserDTO) {
        User loginUser = loginUserDTO.toEntity();
        try {
            Optional<User> optionalUser = userService.getUserByEmail(loginUser.getEmail());
            if (optionalUser.isPresent()) {
                logger.info("Attempting to authenticate user {}", loginUser.getEmail());
                userService.authenticateUser(loginUser.getEmail(), loginUser.getPassword());
                logger.info("User {} authenticated successfully", loginUser.getEmail());
                String token = jwtTokenProvider.generateToken(loginUser.getEmail(), optionalUser.get().getRole());
                Map<String, Object> response = new HashMap<>();
                response.put("email", loginUser.getEmail());
                response.put("token", token);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }
        } catch (AuthenticationException e) {
            logger.error("Authentication failed for user {}", loginUser.getEmail(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email/password supplied");
        }
    }
}