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
import java.util.Set;

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

        Set<Role> userRoles = user.getRoles();

        boolean hasPermission = userRoles.stream()
                .anyMatch(role -> offerStatusPermissionRepository.existsByOfferStatusAndRole(newStatus, role));


        if (!hasPermission) {
            throw new IllegalArgumentException("User does not have permission to change offer status to " + newStatus);
        }

        offer.setStatus(newStatus);
        return offerRepository.save(offer);
    }
}