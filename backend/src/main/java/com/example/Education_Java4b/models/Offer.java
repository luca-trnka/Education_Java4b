package com.example.Education_Java4b.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 5, max = 25, message = "Title size should be between 5 and 25 characters")
    @Column(unique = true)
    private String title;

    @NotBlank
    @Size(min = 5, max = 300, message = "Description size should be between 5 and 300 characters")
    private String description;

    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private User supplier;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToMany
    @JoinTable(
            name = "offer_worker",
            joinColumns = @JoinColumn(name = "offer_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id"))
    private List<User> workers;

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OfferStatus getStatus() {
        return status;
    }

    public void setStatus(OfferStatus status) {
        this.status = status;
    }

    public User getSupplier() {
        return supplier;
    }

    public void setSupplier(User supplier) {
        this.supplier = supplier;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public List<User> getWorkers() {
        return workers;
    }

    public void setWorkers(List<User> workers) {
        this.workers = workers;
    }
}