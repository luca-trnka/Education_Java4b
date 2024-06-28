package com.example.Education_Java4b.services;

import com.example.Education_Java4b.models.Role;
import com.example.Education_Java4b.models.User;
import com.example.Education_Java4b.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User registerUser(User user) {
        logger.info("Registering user: {}", user);
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            logger.warn("User with this email already exists: {}", user.getEmail());
            throw new IllegalArgumentException("User with this email already exists.");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(Role.NEW_USER);
        User savedUser = userRepository.save(user);
        logger.info("Registered user: {}", savedUser);
        return savedUser;
    }

    public Optional<User> authenticateUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return userOptional;
            }
        }
        return Optional.empty();
    }

    public User getUserByIdAndRole(Long id, Role expectedRole) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            if (user.get().getRole().equals(expectedRole)) {
                return user.get();
            } else {
                throw new RuntimeException("User with id: " + id + " is not a " + expectedRole);
            }
        } else {
            throw new RuntimeException("User not found for id: " + id);
        }
    }

    public List<User> getAllUsersByRole(Role role) {
        return userRepository.findAllByRole(role);
    }

}