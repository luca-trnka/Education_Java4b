package com.example.Education_Java4b.repos;

import com.example.Education_Java4b.models.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findBySupplierId(Long supplierId);
}