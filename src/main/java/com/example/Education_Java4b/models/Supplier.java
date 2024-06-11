package com.example.Education_Java4b.models;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Offer> offers;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Worker> workers;

    public Supplier() {
        offers = new ArrayList<>();
        workers = new ArrayList<>();
    }

    public Supplier(String name, String email, List<Offer> offers, List<Worker> workers) {
        this.name = name;
        this.email = email;
        this.offers = offers;
        this.workers = workers;
    }

    public Supplier(Long id, String name, String email, List<Offer> offers, List<Worker> workers) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.offers = offers;
        this.workers = workers;
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

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }
}