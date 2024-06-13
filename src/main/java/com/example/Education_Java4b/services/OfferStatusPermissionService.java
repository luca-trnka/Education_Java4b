package com.example.Education_Java4b.services;

import com.example.Education_Java4b.models.OfferStatusPermission;
import com.example.Education_Java4b.repos.OfferStatusPermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferStatusPermissionService {

    private final OfferStatusPermissionRepository offerStatusPermissionRepository;

    public OfferStatusPermissionService(OfferStatusPermissionRepository offerStatusPermissionRepository) {
        this.offerStatusPermissionRepository = offerStatusPermissionRepository;
    }

    public List<OfferStatusPermission> getAllOfferStatusPermissions() {
        return offerStatusPermissionRepository.findAll();
    }

    public Optional<OfferStatusPermission> getOfferStatusPermissionById(Long id) {
        return offerStatusPermissionRepository.findById(id);
    }

    public OfferStatusPermission createOfferStatusPermission(OfferStatusPermission offerStatusPermission) {
        return offerStatusPermissionRepository.save(offerStatusPermission);
    }

    public OfferStatusPermission updateOfferStatusPermission(OfferStatusPermission offerStatusPermission) {
        return offerStatusPermissionRepository.save(offerStatusPermission);
    }

    public void deleteOfferStatusPermission(Long id) {
        offerStatusPermissionRepository.deleteById(id);
    }
}