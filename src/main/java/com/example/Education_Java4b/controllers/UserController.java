package com.example.Education_Java4b.controllers;

import com.example.Education_Java4b.dtos.UserDTO;
import com.example.Education_Java4b.models.User;
import com.example.Education_Java4b.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try {
            User user = userDTO.toEntity();
            User newUser = userService.registerUser(user);
            return new ResponseEntity<>(UserDTO.fromEntity(newUser), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        try {
            String email = userDTO.getEmail();
            String password = userDTO.getPassword();
            User authenticatedUser = userService.authenticateUser(email, password)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid login."));
            return new ResponseEntity<>(UserDTO.fromEntity(authenticatedUser), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        Optional<User> userOptional = userService.getUserById(userId);
        return userOptional.map(user -> ResponseEntity.ok().body(UserDTO.fromEntity(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        userDTO.setId(userId);
        try {
            User user = userDTO.toEntity();
            User updatedUser = userService.updateUser(user);
            return new ResponseEntity<>(UserDTO.fromEntity(updatedUser), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>("User with id " + userId + " was deleted.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
