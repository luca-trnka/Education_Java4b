package com.example.Education_Java4b.models;

import javax.persistence.*;

@Entity
@Table(name = "offer_status_permissions")
public class OfferStatusPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OfferStatus offerStatus;

    @Enumerated(EnumType.STRING)
    private Role role;

    public OfferStatusPermission() {
    }

    public OfferStatusPermission(OfferStatus offerStatus, Role role) {
        this.offerStatus = offerStatus;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}