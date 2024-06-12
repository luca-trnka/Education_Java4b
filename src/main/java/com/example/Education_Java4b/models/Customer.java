package com.example.Education_Java4b.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends User {
    @OneToMany(mappedBy = "customer")
    private List<Offer> offers;

    public Customer() {
        offers = new ArrayList<>();
    }

    public Customer(Long id, String email, String password, String name) {
        super(id, email, password, name, Role.CUSTOMER);
        offers = new ArrayList<>();
    }
    public Customer(String email, String password, String name) {
        super(email, password, name, Role.CUSTOMER);
        offers = new ArrayList<>();
    }

    public Customer(String email, String password) {
        super(email, password);
        offers = new ArrayList<>();
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}