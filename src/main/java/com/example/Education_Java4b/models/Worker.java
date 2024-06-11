package com.example.Education_Java4b.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @ManyToMany(mappedBy = "workers")
    private List<Offer> offers;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    public Worker() {
        offers = new ArrayList<>();
    }

    public Worker(String name, String email, List<Offer> offers, Supplier supplier) {
        this.name = name;
        this.email = email;
        this.offers = offers;
        this.supplier = supplier;
    }

    public Worker(Long id, String name, String email, List<Offer> offers, Supplier supplier) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.offers = offers;
        this.supplier = supplier;
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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
