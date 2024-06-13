package com.example.Education_Java4b.controllers;

import com.example.Education_Java4b.models.OfferStatusPermission;
import com.example.Education_Java4b.services.OfferStatusPermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offerStatusPermissions")
public class OfferStatusPermissionController {

    private final OfferStatusPermissionService offerStatusPermissionService;

    public OfferStatusPermissionController(OfferStatusPermissionService offerStatusPermissionService) {
        this.offerStatusPermissionService = offerStatusPermissionService;
    }

    @GetMapping
    public List<OfferStatusPermission> getAllOfferStatusPermissions() {
        return offerStatusPermissionService.getAllOfferStatusPermissions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferStatusPermission> getOfferStatusPermissionById(@PathVariable Long id) {
        return offerStatusPermissionService.getOfferStatusPermissionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public OfferStatusPermission createOfferStatusPermission(@RequestBody OfferStatusPermission offerStatusPermission) {
        return offerStatusPermissionService.createOfferStatusPermission(offerStatusPermission);
    }

    @PutMapping("/{id}")
    public OfferStatusPermission updateOfferStatusPermission(@PathVariable Long id, @RequestBody OfferStatusPermission offerStatusPermission) {
        offerStatusPermission.setId(id);
        return offerStatusPermissionService.updateOfferStatusPermission(offerStatusPermission);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOfferStatusPermission(@PathVariable Long id) {
        offerStatusPermissionService.deleteOfferStatusPermission(id);
        return ResponseEntity.noContent().build();
    }
}