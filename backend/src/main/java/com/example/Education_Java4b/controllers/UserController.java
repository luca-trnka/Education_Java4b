package com.example.Education_Java4b.controllers;

import com.example.Education_Java4b.dtos.UserDTO;
import com.example.Education_Java4b.exceptions.ResourceNotFoundException;
import com.example.Education_Java4b.models.Role;
import com.example.Education_Java4b.models.User;
import com.example.Education_Java4b.services.AuthenticationService;
import com.example.Education_Java4b.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authenticated user: " + authentication.getName());
        System.out.println("Authenticated authorities: " + authentication.getAuthorities());
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        userDTO.setId(userId);
        try {
            User user = userDTO.toEntity();
            User updatedUser = userService.updateUser(user);
            return new ResponseEntity<>(UserDTO.fromEntity(updatedUser), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>("User with id " + userId + " was deleted.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/validateToken")
    public ResponseEntity<?> validateToken(@RequestBody String token) {
        boolean isValid = authenticationService.authenticate(token);
        if (isValid) {
            return new ResponseEntity<>("Token is valid", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Token is invalid", HttpStatus.UNAUTHORIZED);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/customers")
    public ResponseEntity<List<User>> getAllCustomers() {
        List<User> customers = userService.getAllUsersByRole(Role.CUSTOMER);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/suppliers")
    public ResponseEntity<List<User>> getAllSuppliers() {
        List<User> suppliers = userService.getAllUsersByRole(Role.SUPPLIER);
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/workers")
    public ResponseEntity<List<User>> getAllWorkers() {
        List<User> workers = userService.getAllUsersByRole(Role.WORKER);
        return new ResponseEntity<>(workers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/newuser")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try {
            User user = userDTO.toEntity();
            User newUser = userService.createUser(user);
            return new ResponseEntity<>(UserDTO.fromEntity(newUser), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
