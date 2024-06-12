package com.example.Education_Java4b.dtos;

import com.example.Education_Java4b.models.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import com.example.Education_Java4b.models.Customer;
import com.example.Education_Java4b.models.Offer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerDTO {
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

    private List<Long> offerIds;

    public CustomerDTO() {}

    public CustomerDTO(Long id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public CustomerDTO(Long id, String email, String password, String name, List<Long> offerIds) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.offerIds = offerIds;
    }

    public CustomerDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static CustomerDTO fromEntity(Customer customer) {
        List<Long> offerIds = customer.getOffers().stream()
                .map(Offer::getId)
                .collect(Collectors.toList());

        return new CustomerDTO(
                customer.getId(),
                customer.getEmail(),
                customer.getPassword(),
                customer.getName(),
                offerIds
        );
    }

    public Customer toEntity() {
        Customer customer = new Customer();
        customer.setId(this.id);
        customer.setEmail(this.email);
        customer.setPassword(this.password);
        customer.setName(this.name);
        List<Offer> offers = new ArrayList<>();
        if (this.offerIds == null) {
            this.offerIds = new ArrayList<>();
        }
        if (this.offerIds != null || !this.offerIds.isEmpty()) {
            offers = this.offerIds.stream()
                    .map(offerId -> {
                        Offer offer = new Offer();
                        offer.setId(offerId);
                        return offer;
                    })
                    .collect(Collectors.toList());
        }
        customer.setOffers(offers);
        return customer;
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

    public List<Long> getOfferIds() {
        return offerIds;
    }

    public void setOfferIds(List<Long> offerIds) {
        this.offerIds = offerIds;
    }
}