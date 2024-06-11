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

public class SupplierDTO {
    private Long id;
    @NotBlank
    @Size(min = 5,max = 25, message = "Name size should be between 5 and 25 characters")
    private String name;
    @NotBlank
    @Email(regexp=".+@.+\\..+", message = "Email formal is not valid")
    private String email;
    private List<Long> offerIds;
    private List<Long> workerIds;

    public SupplierDTO() {
    }


    public SupplierDTO(Long id, String name, String email, List<Long> offerIds, List<Long> workerIds) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.offerIds = (offerIds != null) ? offerIds : new ArrayList<>();
        this.workerIds = (workerIds!= null) ? workerIds : new ArrayList<>();
    }

    public SupplierDTO(Long id, String name, String email, List<Long> workerIds) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.workerIds = workerIds = (workerIds!= null) ? workerIds : new ArrayList<>();
        this.offerIds = new ArrayList<>();
    }

    public SupplierDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.offerIds = new ArrayList<>();
        this.workerIds = new ArrayList<>();
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

    public List<Long> getWorkerIds() {
        return workerIds;
    }

    public void setWorkerIds(List<Long> workerIds) {
        this.workerIds = workerIds;
    }

    public Supplier toEntity() {
        Supplier supplier = new Supplier();
        supplier.setId(this.id);
        supplier.setName(this.name);
        supplier.setEmail(this.email);
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
        supplier.setOffers(offers);

        List<Worker> workers = new ArrayList<>();
        if (this.workerIds != null || !this.offerIds.isEmpty()) {
            workers = this.workerIds.stream()
                    .map(workerId -> {
                        Worker worker = new Worker();
                        worker.setId(workerId);
                        return worker;
                    })
                    .collect(Collectors.toList());
        }
        supplier.setWorkers(workers);

        return supplier;
    }

    public static SupplierDTO fromEntity(Supplier supplier) {
        List<Long> offerIds = supplier.getOffers().stream()
                .map(Offer::getId)
                .collect(Collectors.toList());

        List<Long> workerIds = supplier.getWorkers().stream()
                .map(Worker::getId)
                .collect(Collectors.toList());

        return new SupplierDTO(
                supplier.getId(),
                supplier.getName(),
                supplier.getEmail(),
                offerIds,
                workerIds
        );
    }
}
