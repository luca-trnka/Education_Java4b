package com.example.Education_Java4b.services;

import com.example.Education_Java4b.models.*;
import com.example.Education_Java4b.repos.*;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {
    private final OfferRepository offerRepository;
    private final WorkerRepository workerRepository;
    private final SupplierRepository supplierRepository;
    private final CustomerRepository customerRepository;

    public OfferService(OfferRepository offerRepository, WorkerRepository workerRepository, SupplierRepository supplierRepository, CustomerRepository customerRepository) {
        this.offerRepository = offerRepository;
        this.workerRepository = workerRepository;
        this.supplierRepository = supplierRepository;
        this.customerRepository = customerRepository;
    }

    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    public Optional<Offer> getOfferById(Long id) {
        return offerRepository.findById(id);
    }

    public void createOffer(Long supplierId, Long customerId, Offer offer) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        offer.setSupplier(supplier);
        offer.setCustomer(customer);

        supplier.getOffers().add(offer);
        customer.getOffers().add(offer);
        offerRepository.save(offer);;
    }

    public void updateOffer(Offer offer) {
        offerRepository.save(offer);
    }

    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }

    public Optional<OfferStatus> getOfferStatus(Long offerId) {
        return offerRepository.findById(offerId).map(Offer::getStatus);
    }

    public void updateOfferStatus(Long offerId, OfferStatus status) {
        offerRepository.findById(offerId).ifPresent(offer -> {
            offer.setStatus(status);
            offerRepository.save(offer);
        });
    }

    public void addWorkerToOffer(Long offerId, Long workerId) {
        offerRepository.findById(offerId).ifPresent(offer -> {
            workerRepository.findById(workerId).ifPresent(worker -> {
                offer.getWorkers().add(worker);
                offerRepository.save(offer);
            });
        });
    }

    public void removeWorkerFromOffer(Long offerId, Long workerId) {
        offerRepository.findById(offerId).ifPresent(offer -> {
            offer.getWorkers().removeIf(worker -> worker.getId().equals(workerId));
            offerRepository.save(offer);
        });
    }

    public boolean offerExists(Long id) {
        return offerRepository.existsById(id);
    }
}
