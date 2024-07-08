package com.example.Education_Java4b.controllers;

import com.example.Education_Java4b.exceptions.ResourceNotFoundException;
import com.example.Education_Java4b.models.Offer;
import com.example.Education_Java4b.dtos.OfferDTO;
import com.example.Education_Java4b.models.OfferStatus;
import com.example.Education_Java4b.models.User;
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
import java.util.List;
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

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{offerId}")
    public ResponseEntity<?> deleteOffer(@PathVariable Long offerId) {
        try {
            offerService.deleteOffer(offerId);
            return new ResponseEntity<>("Offer with id " + offerId + " was deleted.", HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{offerId}/workers")
    public ResponseEntity<?> addWorkerToOffer(@PathVariable Long offerId, @RequestBody Long workerId) {
        try {
            Offer updatedOffer = offerService.addWorkerToOffer(offerId, workerId);
            return new ResponseEntity<>(OfferDTO.fromEntity(updatedOffer), HttpStatus.OK);
        } catch (IllegalArgumentException | ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{offerId}/workers")
    public ResponseEntity<?> removeWorkerFromOffer(@PathVariable Long offerId, @RequestBody Long workerId) {
        try {
            Offer updatedOffer = offerService.removeWorkerFromOffer(offerId, workerId);
            return new ResponseEntity<>(OfferDTO.fromEntity(updatedOffer), HttpStatus.OK);
        } catch (IllegalArgumentException | ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('SUPPLIER')")
    @GetMapping("/suppliers-offers")
    public ResponseEntity<?> getSuppliersOffers(Authentication authentication) {
        String email = authentication.getName();

        User user = userService.getUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Offer> offers = offerService.getOffersBySupplierId(user.getId());
        return ResponseEntity.ok(offers);
    }
}