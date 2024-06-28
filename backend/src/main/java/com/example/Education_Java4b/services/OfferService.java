package com.example.Education_Java4b.services;

import com.example.Education_Java4b.exceptions.ResourceNotFoundException;
import com.example.Education_Java4b.models.Offer;
import com.example.Education_Java4b.models.OfferStatus;
import com.example.Education_Java4b.models.Role;
import com.example.Education_Java4b.models.User;
import com.example.Education_Java4b.repos.OfferRepository;
import com.example.Education_Java4b.repos.OfferStatusPermissionRepository;
import com.example.Education_Java4b.repos.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final OfferStatusPermissionRepository offerStatusPermissionRepository;

    public OfferService(OfferRepository offerRepository, UserRepository userRepository, OfferStatusPermissionRepository offerStatusPermissionRepository) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.offerStatusPermissionRepository = offerStatusPermissionRepository;
    }

    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    public Optional<Offer> getOfferById(Long id) {
        return offerRepository.findById(id);
    }

    public Offer createOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    public Offer updateOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }

    public Offer changeOfferStatus(Long offerId, OfferStatus newStatus, Long userId) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new ResourceNotFoundException("Offer not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Role userRole = user.getRole();

        boolean hasPermission = offerStatusPermissionRepository.existsByOfferStatusAndRole(newStatus, userRole);

        if (!hasPermission) {
            throw new IllegalArgumentException("User does not have permission to change offer status to " + newStatus);
        }

        offer.setStatus(newStatus);
        return offerRepository.save(offer);
    }

    public Offer addWorkerToOffer(Long offerId, Long workerId) throws ResourceNotFoundException {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new ResourceNotFoundException("Offer not found with id " + offerId));

        User worker = userRepository.findById(workerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + workerId));

        offer.getWorkers().add(worker);
        return offerRepository.save(offer);
    }

    public Offer removeWorkerFromOffer(Long offerId, Long workerId) throws ResourceNotFoundException {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new ResourceNotFoundException("Offer not found with id " + offerId));

        User worker = userRepository.findById(workerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + workerId));

        offer.getWorkers().remove(worker);
        return offerRepository.save(offer);
    }

}