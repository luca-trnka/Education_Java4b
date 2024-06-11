package com.example.Education_Java4b.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.example.Education_Java4b.models.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OfferDTO {
    private Long id;
    @NotBlank
    @Size(min = 5,max = 25, message = "Description size should be between 5 and 25 characters")
    private String description;
    private OfferStatus status;
    private Long customerId;
    private Long supplierId;
    private List<Long> workerIds;

    public OfferDTO() {
        workerIds = new ArrayList<>();
    }

    public OfferDTO(Long id, String description, OfferStatus status, Long customerId, Long supplierId, List<Long> workerIds) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.customerId = customerId;
        this.supplierId = supplierId;
        this.workerIds = workerIds;
    }

    public OfferDTO(Long id, String description, Long customerId, Long supplierId) {
        this(id, description, OfferStatus.NEW, customerId, supplierId, new ArrayList<>());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public List<Long> getWorkerIds() {
        return workerIds;
    }

    public void setWorkerIds(List<Long> workerIds) {
        this.workerIds = workerIds;
    }

    public static OfferDTO fromEntity(Offer offer) {
        OfferDTO dto = new OfferDTO();
        dto.setId(offer.getId());
        dto.setDescription(offer.getDescription());
        dto.setStatus(offer.getStatus());
        dto.setCustomerId(offer.getCustomer() != null ? offer.getCustomer().getId() : null);
        dto.setSupplierId(offer.getSupplier() != null ? offer.getSupplier().getId() : null);
        dto.setWorkerIds(offer.getWorkers().stream().map(Worker::getId).collect(Collectors.toList()));
        return dto;
    }

    public Offer toEntity() {
        Offer offer = new Offer();
        offer.setId(this.id);
        offer.setDescription(this.description);
        if (this.status == null) {
            this.status = OfferStatus.NEW;
        }
        if (!Arrays.asList(OfferStatus.values()).contains(this.status)) {
            throw new IllegalArgumentException("Invalid status value: " + this.status);
        }
        offer.setStatus(this.status);

        if (this.customerId != null) {
            Customer customer = new Customer();
            customer.setId(this.customerId);
            offer.setCustomer(customer);
        }

        if (this.supplierId != null) {
            Supplier supplier = new Supplier();
            supplier.setId(this.supplierId);
            offer.setSupplier(supplier);
        }

        List<Worker> workers = new ArrayList<>();
        if (this.workerIds != null) {
            for (Long workerId : this.workerIds) {
                Worker worker = new Worker();
                worker.setId(workerId);
                workers.add(worker);
            }
        }
        offer.setWorkers(workers);

        return offer;
    }
}
