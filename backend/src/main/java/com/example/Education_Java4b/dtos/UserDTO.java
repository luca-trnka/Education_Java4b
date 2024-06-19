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
    private Set<String> roles;

    public UserDTO(Long id, String email, String password, String name, Set<String> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.roles = roles;
    }

    public UserDTO(String email, String password, String name, Set<String> roles) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.roles = roles;
    }

    public UserDTO(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.roles = Set.of(Role.NEW_USER.name());
    }

    public UserDTO() {
    }

    public static UserDTO fromEntity(User user) {
        if (user == null) {
            return null;
        }
        Set<String> roles = user.getRoles() != null ? user.getRoles().stream()
                .map(Enum::name)
                .collect(Collectors.toSet()) : Set.of(Role.NEW_USER.name());
        return new UserDTO(user.getId(), user.getEmail(), user.getPassword(), user.getName(), roles);
    }

    public User toEntity() {
        Set<Role> roles = this.roles != null ? this.roles.stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet()) : Set.of(Role.NEW_USER);
        return new User(this.id, this.email, this.password, this.name, roles);
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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}