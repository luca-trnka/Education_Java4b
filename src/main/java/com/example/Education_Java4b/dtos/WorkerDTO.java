package com.example.Education_Java4b.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.example.Education_Java4b.models.Offer;
import com.example.Education_Java4b.models.Supplier;
import com.example.Education_Java4b.models.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WorkerDTO {
    private Long id;
    @NotBlank
    @Size(min = 5,max = 25, message = "Name size should be between 5 and 25 characters")
    private String name;
    @NotBlank
    @Email(regexp=".+@.+\\..+", message = "Email formal is not valid")
    private String email;
    private List<Long> offerIds;
    private Long supplierId;

    public WorkerDTO() {}

    public WorkerDTO(Long id, String name, String email, List<Long> offerIds, Long supplierId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.offerIds = (offerIds != null) ? offerIds : new ArrayList<>();
        this.supplierId = supplierId;
    }

    public WorkerDTO(Long id, String name, String email, Long supplierId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.offerIds = new ArrayList<>();
        this.supplierId = supplierId;
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

    public List<Long> getOfferIds() {
        return offerIds;
    }

    public void setOfferIds(List<Long> offerIds) {
        this.offerIds = offerIds;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public static WorkerDTO fromEntity(Worker worker) {
        List<Long> offerIds = worker.getOffers().stream().map(Offer::getId).collect(Collectors.toList());
        return new WorkerDTO(
                worker.getId(),
                worker.getName(),
                worker.getEmail(),
                offerIds,
                worker.getSupplier() != null ? worker.getSupplier().getId() : null
        );
    }

    public Worker toEntity() {
        Worker worker = new Worker();
        worker.setId(this.id);
        worker.setName(this.name);
        worker.setEmail(this.email);

        if (this.supplierId != null) {
            Supplier supplier = new Supplier();
            supplier.setId(this.supplierId);
            worker.setSupplier(supplier);
        }
        if (this.offerIds == null) {
            this.offerIds = new ArrayList<>();
        }
        List<Offer> offers = new ArrayList<>();
        if (this.offerIds != null || !this.offerIds.isEmpty()) {
            offers = this.offerIds.stream()
                    .map(offerId -> {
                        Offer offer = new Offer();
                        offer.setId(offerId);
                        return offer;
                    })
                    .collect(Collectors.toList());
        }
        worker.setOffers(offers);

        return worker;
    }
}