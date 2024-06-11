package com.example.Education_Java4b.controllers;

import jakarta.validation.Valid;
import com.example.Education_Java4b.dtos.OfferDTO;
import com.example.Education_Java4b.exceptions.ResourceNotFoundException;
import com.example.Education_Java4b.models.Offer;
import com.example.Education_Java4b.models.OfferStatus;
import com.example.Education_Java4b.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/offers")
public class OfferController {
    private final OfferService offerService;
    private final WorkerService workerService;
    private final CustomerService customerService;
    private final SupplierService supplierService;

    public OfferController(OfferService offerService, WorkerService workerService, CustomerService customerService, SupplierService supplierService) {
        this.offerService = offerService;
        this.workerService = workerService;
        this.customerService = customerService;
        this.supplierService = supplierService;
    }

    @GetMapping
    public List<OfferDTO> getAllOffers() {
        return offerService.getAllOffers().stream()
                .map(OfferDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OfferDTO getOfferById(@PathVariable Long id) {
        return offerService.getOfferById(id)
                .map(OfferDTO::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Offer with id " + id + " not found"));
    }

    @PostMapping
    public OfferDTO createOffer(@RequestParam Long supplierId, @RequestParam Long customerId, @RequestBody @Valid OfferDTO offerDTO) {
        if (!supplierService.supplierExists(supplierId)) {
            throw new ResourceNotFoundException("Supplier with id " + supplierId + " not found");
        }
        if (!customerService.customerExists(customerId)) {
            throw new ResourceNotFoundException("Customer with id " + customerId + " not found");
        }
        Offer offer = offerDTO.toEntity();
        offerService.createOffer(supplierId, customerId, offer);
        return OfferDTO.fromEntity(offer);
    }

    @PutMapping("/{id}")
    public void updateOffer(@PathVariable Long id, @RequestBody OfferDTO offerDTO) {
        if (!offerService.offerExists(id)) {
            throw new ResourceNotFoundException("Offer with id " + id + " not found");
        }
        Offer offer = offerDTO.toEntity();
        offer.setId(id);
        offerService.updateOffer(offer);
    }

    @DeleteMapping("/{id}")
    public void deleteOffer(@PathVariable Long id) {
        offerService.deleteOffer(id);
    }

    @GetMapping("/{id}/status")
    public OfferStatus getOfferStatus(@PathVariable Long id) {
        return offerService.getOfferStatus(id)
                .orElseThrow(() -> new ResourceNotFoundException("Offer with id " + id + " not found"));
    }

    @PutMapping("/{id}/status")
    public void updateOfferStatus(@PathVariable Long id, @RequestParam OfferStatus status) {
        if (!offerService.offerExists(id)) {
            throw new ResourceNotFoundException("Offer with id " + id + " not found");
        }
        if (!Arrays.asList(OfferStatus.values()).contains(status)) {
            throw new IllegalArgumentException("Invalid status value: " + status);
        }
        offerService.updateOfferStatus(id, status);
    }

    @PostMapping("/{id}/workers/{workerId}")
    public void addWorkerToOffer(@PathVariable Long id, @PathVariable Long workerId) {
        if (!offerService.offerExists(id)) {
            throw new ResourceNotFoundException("Offer with id " + id + " not found");
        }
        if (!workerService.workerExists(workerId)) {
            throw new ResourceNotFoundException("Worker with id " + workerId + " not found");
        }
        offerService.addWorkerToOffer(id, workerId);
    }

    @DeleteMapping("/{id}/workers/{workerId}")
    public void removeWorkerFromOffer(@PathVariable Long id, @PathVariable Long workerId) {
        if (!offerService.offerExists(id)) {
            throw new ResourceNotFoundException("Offer with id " + id + " not found");
        }
        if (!workerService.workerExists(workerId)) {
            throw new ResourceNotFoundException("Worker with id " + workerId + " not found");
        }
        offerService.removeWorkerFromOffer(id, workerId);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}