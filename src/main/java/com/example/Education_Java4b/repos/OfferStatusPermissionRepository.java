package com.example.Education_Java4b.repos;

import com.example.Education_Java4b.models.OfferStatus;
import com.example.Education_Java4b.models.OfferStatusPermission;
import com.example.Education_Java4b.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferStatusPermissionRepository extends JpaRepository<OfferStatusPermission, Long> {
    List<OfferStatusPermission> findByRole(Role role);
    boolean existsByOfferStatusAndRole(OfferStatus offerStatus, Role role);
}