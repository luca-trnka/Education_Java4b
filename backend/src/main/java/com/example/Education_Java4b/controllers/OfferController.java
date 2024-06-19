package com.example.Education_Java4b.controllers;

import com.example.Education_Java4b.exceptions.ResourceNotFoundException;
import com.example.Education_Java4b.models.Offer;
import com.example.Education_Java4b.models.OfferStatus;
import com.example.Education_Java4b.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;

    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createOffer(@Valid @RequestBody Offer offer) {
        try {
            Offer newOffer = offerService.createOffer(offer);
            return new ResponseEntity<>(newOffer, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{offerId}")
    public ResponseEntity<?> getOfferById(@PathVariable Long offerId) {
        try {
            Optional<Offer> offer = offerService.getOfferById(offerId);
            return offer.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{offerId}")
    public ResponseEntity<?> updateOffer(@PathVariable Long offerId, @RequestBody Offer offer) {
        try {
            offer.setId(offerId);
            Offer updatedOffer = offerService.updateOffer(offer);
            return new ResponseEntity<>(updatedOffer, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{offerId}/status")
    public ResponseEntity<?> changeOfferStatus(@PathVariable Long offerId, @RequestParam OfferStatus status, @RequestParam Long userId) {
        try {
            Offer updatedOffer = offerService.changeOfferStatus(offerId, status, userId);
            return new ResponseEntity<>(updatedOffer, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{offerId}")
    public ResponseEntity<?> deleteOffer(@PathVariable Long offerId) {
        try {
            offerService.deleteOffer(offerId);
            return new ResponseEntity<>("Offer with id " + offerId + " was deleted.", HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}