package com.example.Education_Java4b.dtos;

import com.example.Education_Java4b.models.Role;
import com.example.Education_Java4b.models.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String role;

    public UserDTO(Long id, String email, String password, String name, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public UserDTO(String email, String password, String name, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public UserDTO(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = Role.NEW_USER.name();
    }

    public UserDTO() {
    }

    public static UserDTO fromEntity(User user) {
        if (user == null) {
            return null;
        }
        String role = user.getRole() != null ? user.getRole().name() : Role.NEW_USER.name();
        return new UserDTO(user.getId(), user.getEmail(), user.getPassword(), user.getName(), role);
    }

    public User toEntity() {
        Role role = this.role != null ? Role.valueOf(this.role) : Role.NEW_USER;
        return new User(this.id, this.email, this.password, this.name, role);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}