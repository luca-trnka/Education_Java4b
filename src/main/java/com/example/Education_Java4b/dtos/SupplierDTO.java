package com.example.Education_Java4b.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @Email(regexp=".+@.+\\..+", message = "Email formal is not valid")
    private String email;

    @NotBlank
    @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must have at least 8 characters, one uppercase letter, one number and one special character")
    private String password;

    @NotBlank
    @Size(min = 5, max = 25, message = "Name size should be between 5 and 25 characters")
    private String name;

    private List<Long> offerIds;
    private List<Long> workerIds;

    public SupplierDTO(Long id, String email, String password, String name, List<Long> offerIds, List<Long> workerIds) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.offerIds = (offerIds != null) ? offerIds : new ArrayList<>();
        this.workerIds = (workerIds!= null) ? workerIds : new ArrayList<>();
    }

    public SupplierDTO(Long id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.offerIds = new ArrayList<>();
        this.workerIds = new ArrayList<>();
    }

    public SupplierDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public SupplierDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public @NotBlank @Size(min = 5, max = 25, message = "Name size should be between 5 and 25 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank @Size(min = 5, max = 25, message = "Name size should be between 5 and 25 characters") String name) {
        this.name = name;
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
        supplier.setEmail(this.email);
        supplier.setPassword(this.password);
        supplier.setName(this.name);
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
                supplier.getEmail(),
                supplier.getPassword(),
                supplier.getName(),
                offerIds,
                workerIds
        );
    }
}
