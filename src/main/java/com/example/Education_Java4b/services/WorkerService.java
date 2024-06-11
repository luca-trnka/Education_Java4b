package com.example.Education_Java4b.services;

import com.example.Education_Java4b.models.Supplier;
import com.example.Education_Java4b.models.Worker;
import com.example.Education_Java4b.repos.SupplierRepository;
import com.example.Education_Java4b.repos.WorkerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    private final WorkerRepository workerRepository;
    private final SupplierRepository supplierRepository;

    public WorkerService(WorkerRepository workerRepository, SupplierRepository supplierRepository) {
        this.workerRepository = workerRepository;
        this.supplierRepository = supplierRepository;
    }

    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    public Optional<Worker> getWorkerById(Long id) {
        return workerRepository.findById(id);
    }

    public void createWorker(Long supplierId, Worker worker) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        worker.setSupplier(supplier);
        supplier.getWorkers().add(worker);

        workerRepository.save(worker);
    }

    public void updateWorker(Worker worker) {
        workerRepository.save(worker);
    }

    public void deleteWorker(Long id) {
        workerRepository.deleteById(id);
    }

    public boolean workerExists(Long id) {
        return workerRepository.existsById(id);
    }

    public boolean workerExistsByName(String name) {
        return workerRepository.findByName(name).isPresent();
    }

    public boolean workerExistsByEmail(String email) {
        return workerRepository.findByEmail(email).isPresent();
    }
}