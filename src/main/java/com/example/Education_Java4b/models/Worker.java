package com.example.Education_Java4b.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Worker extends User {

    @ManyToMany(mappedBy = "workers")
    private List<Offer> offers;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    public Worker(Long id, String email, String password, String name, Role role, Supplier supplier) {
        super(id, email, password, name, role);
        this.supplier = supplier;
        this.offers = new ArrayList<>();
    }

    public Worker(Long id, String email, String password, String name, Role role, Supplier supplier, List<Offer> offers) {
        super(id, email, password, name, role);
        this.supplier = supplier;
        this.offers = offers;
    }

    public Worker(String email, String password, String name, Role role) {
        super(email, password, name, role);
    }

    public Worker(String email, String password) {
        super(email, password);
    }

    public Worker() {
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
