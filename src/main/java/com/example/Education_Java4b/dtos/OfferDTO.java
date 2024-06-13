package com.example.Education_Java4b.dtos;

import com.example.Education_Java4b.models.Offer;
import com.example.Education_Java4b.models.OfferStatus;
import com.example.Education_Java4b.models.User;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.List;
import java.util.stream.Collectors;

public class OfferDTO {
    private Long id;
    @NotBlank
    @Size(min = 5, max = 25, message = "Title size should be between 5 and 25 characters")
    private String title;

    @NotBlank
    @Size(min = 5, max = 300, message = "Description size should be between 5 and 300 characters")
    private String description;

    private String status;
    private Long supplierId;
    private Long customerId;
    private List<Long> workerIds;

    public OfferDTO(Long id, String title, String description, String status, Long supplierId, Long customerId, List<Long> workerIds) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.supplierId = supplierId;
        this.customerId = customerId;
        this.workerIds = workerIds;
    }

    public OfferDTO() {
    }

    public static OfferDTO fromEntity(Offer offer) {
        return new OfferDTO(
                offer.getId(),
                offer.getTitle(),
                offer.getDescription(),
                offer.getStatus().name(),
                offer.getSupplier().getId(),
                offer.getCustomer().getId(),
                offer.getWorkers().stream().map(User::getId).collect(Collectors.toList())
        );
    }

    public Offer toEntity() {
        Offer offer = new Offer();
        offer.setId(this.id);
        offer.setTitle(this.title);
        offer.setDescription(this.description);
        offer.setStatus(OfferStatus.valueOf(this.status));
        // Note: You need to fetch the User entities for supplier, customer and workers from the database
        return offer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank @Size(min = 5, max = 25, message = "Title size should be between 5 and 25 characters") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank @Size(min = 5, max = 25, message = "Title size should be between 5 and 25 characters") String title) {
        this.title = title;
    }

    public @NotBlank @Size(min = 5, max = 300, message = "Description size should be between 5 and 300 characters") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank @Size(min = 5, max = 300, message = "Description size should be between 5 and 300 characters") String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getWorkerIds() {
        return workerIds;
    }

    public void setWorkerIds(List<Long> workerIds) {
        this.workerIds = workerIds;
    }

    // getters and setters
}