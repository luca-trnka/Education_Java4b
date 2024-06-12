package com.example.Education_Java4b.models;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Supplier extends User {

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Offer> offers;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Worker> workers;

    public Supplier() {
        offers = new ArrayList<>();
        workers = new ArrayList<>();
    }

    public Supplier(Long id, String email, String password, String name, Role role, List<Offer> offers, List<Worker> workers) {
        super(id, email, password, name, role);
        this.offers = offers;
        this.workers = workers;
    }

    public Supplier(String email, String password, String name, Role role, List<Offer> offers, List<Worker> workers) {
        super(email, password, name, role);
        this.offers = offers;
        this.workers = workers;
    }

    public Supplier(String email, String password) {
        super(email, password);
        offers = new ArrayList<>();
        workers = new ArrayList<>();
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