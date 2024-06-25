package com.example.Education_Java4b.controllers;

import com.example.Education_Java4b.exceptions.ResourceNotFoundException;
import com.example.Education_Java4b.models.Offer;
import com.example.Education_Java4b.dtos.OfferDTO;
import com.example.Education_Java4b.models.OfferStatus;
import com.example.Education_Java4b.services.AuthenticationService;
import com.example.Education_Java4b.services.OfferService;
import com.example.Education_Java4b.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/offers")
@CrossOrigin(origins = "http://localhost:3000")
public class OfferController {

    private final OfferService offerService;
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @Autowired
    public OfferController(OfferService offerService, AuthenticationService authenticationService, UserService userService) {
        this.offerService = offerService;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createOffer(@Valid @RequestBody OfferDTO offerDTO) {
        try {
            Offer offer = offerDTO.toEntity(userService);
            Offer newOffer = offerService.createOffer(offer);
            return new ResponseEntity<>(OfferDTO.fromEntity(newOffer), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllOffers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authenticated user: " + authentication.getName());
        System.out.println("Authenticated authorities: " + authentication.getAuthorities());
        return new ResponseEntity<>(offerService.getAllOffers(), HttpStatus.OK);
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
    public ResponseEntity<?> updateOffer(@PathVariable Long offerId, @RequestBody OfferDTO offerDTO) {
        offerDTO.setId(offerId);
        try {
            Offer offer = offerDTO.toEntity(userService);
            Offer updatedOffer = offerService.updateOffer(offer);
            return new ResponseEntity<>(OfferDTO.fromEntity(updatedOffer), HttpStatus.OK);
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