package com.example.Education_Java4b.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @OneToMany(mappedBy = "customer")
    private List<Offer> offers;

    public Customer() {
        offers = new ArrayList<>();
    }

    public Customer(String name, String email, List<Offer> offers) {
        this.name = name;
        this.email = email;
        this.offers = offers;
    }

    public Customer(Long id, String name, String email, List<Offer> offers) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.offers = offers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}