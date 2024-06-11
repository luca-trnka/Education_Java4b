package com.example.Education_Java4b.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.example.Education_Java4b.models.Customer;
import com.example.Education_Java4b.models.Offer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerDTO {
    private Long id;
    @NotBlank
    @Size(min = 5,max = 25, message = "Name size should be between 5 and 25 characters")
    private String name;
    @NotBlank
    @Email(regexp=".+@.+\\..+", message = "Email formal is not valid")
    private String email;
    private List<Long> offerIds;

    public CustomerDTO() {}

    public CustomerDTO(Long id, String name, String email, List<Long> offerIds) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.offerIds = offerIds;
    }

    public CustomerDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.offerIds = new ArrayList<>();
    }

    public static CustomerDTO fromEntity(Customer customer) {
        List<Long> offerIds = customer.getOffers().stream()
                .map(Offer::getId)
                .collect(Collectors.toList());

        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                offerIds
        );
    }

    public Customer toEntity() {
        Customer customer = new Customer();
        customer.setId(this.id);
        customer.setName(this.name);
        customer.setEmail(this.email);
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

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<Long> getOfferIds() { return offerIds; }
    public void setOfferIds(List<Long> offerIds) { this.offerIds = offerIds; }
}