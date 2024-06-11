package com.example.Education_Java4b.services;

import com.example.Education_Java4b.models.Supplier;
import com.example.Education_Java4b.repos.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Optional<Supplier> getSupplierById(Long id) {
        return supplierRepository.findById(id);
    }

    public void createSupplier(Supplier supplier) {
        supplierRepository.save(supplier);
    }

    public void updateSupplier(Supplier supplier) {
        supplierRepository.save(supplier);
    }

    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }

    public boolean supplierExists(Long id) {
        return supplierRepository.existsById(id);
    }

    public boolean supplierExistsByName(String name) {
        return supplierRepository.findByName(name).isPresent();
    }

    public boolean supplierExistsByEmail(String email) {
        return supplierRepository.findByEmail(email).isPresent();
    }
}