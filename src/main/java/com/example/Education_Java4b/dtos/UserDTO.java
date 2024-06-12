package com.example.Education_Java4b.dtos;

import com.example.Education_Java4b.models.Customer;
import com.example.Education_Java4b.models.Supplier;
import com.example.Education_Java4b.models.User;
import com.example.Education_Java4b.models.Worker;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDTO {
    private Long id;
    @NotBlank
    @Email(regexp=".+@.+\\..+", message = "Email formal is not valid")
    private String email;

    @NotBlank
    @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must have at least 8 characters, one uppercase letter, one number and one special character")
    private String password;

    @NotBlank
    @Size(min = 5, max = 25, message = "Name size should be between 5 and 25 characters")
    private String name;

    private String role;

    public UserDTO(Long id, String email, String password, String name, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserDTO() {
    }

    public static UserDTO fromEntity(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getName(),
                user.getRole().name()
        );
    }

    public User toEntity() {
        User user;
        switch (this.role) {
            case "CUSTOMER":
                user = new Customer();
                break;
            case "SUPPLIER":
                user = new Supplier();
                break;
            case "WORKER":
                user = new Worker();
                break;
            default:
                throw new IllegalArgumentException("Invalid role: " + this.role);
        }
        user.setId(this.id);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setName(this.name);
        return user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public @NotBlank @Size(min = 5, max = 25, message = "Name size should be between 5 and 25 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank @Size(min = 5, max = 25, message = "Name size should be between 5 and 25 characters") String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}