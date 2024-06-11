package com.example.Education_Java4b.controllers;

import jakarta.validation.Valid;
import com.example.Education_Java4b.dtos.WorkerDTO;
import com.example.Education_Java4b.exceptions.ResourceNotFoundException;
import com.example.Education_Java4b.models.Worker;
import com.example.Education_Java4b.services.SupplierService;
import com.example.Education_Java4b.services.WorkerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/workers")
public class WorkerController {
    private final WorkerService workerService;
    private final SupplierService supplierService;


    public WorkerController(WorkerService workerService, SupplierService supplierService) {
        this.workerService = workerService;
        this.supplierService = supplierService;
    }

    @GetMapping
    public List<WorkerDTO> getAllWorkers() {
        return workerService.getAllWorkers().stream()
                .map(WorkerDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public WorkerDTO getWorkerById(@PathVariable Long id) {
        return workerService.getWorkerById(id)
                .map(WorkerDTO::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Worker with " + id + " not found\""));
    }

    @PostMapping
    public WorkerDTO createWorker(@RequestParam Long supplierId, @Valid @RequestBody WorkerDTO workerDTO) {
        if (!supplierService.supplierExists(supplierId)) {
            throw new ResourceNotFoundException("Supplier with id " + supplierId + " not found");
        }
        if (workerService.workerExistsByName(workerDTO.getName())) {
            throw new IllegalArgumentException("A worker with this name already exists");
        }
        if (workerService.workerExistsByEmail(workerDTO.getEmail())) {
            throw new IllegalArgumentException("A worker with this email already exists");
        }
        Worker worker = workerDTO.toEntity();
        workerService.createWorker(supplierId, worker);
        return WorkerDTO.fromEntity(worker);
    }

    @PutMapping("/{id}")
    public void updateWorker(@PathVariable Long id, @Valid @RequestBody WorkerDTO workerDTO) {
        if (!workerService.workerExists(id)) {
            throw new ResourceNotFoundException("Worker with id " + id + " not found");
        }
        Worker worker = workerDTO.toEntity();
        worker.setId(id);
        workerService.updateWorker(worker);
    }

    @DeleteMapping("/{id}")
    public void deleteWorker(@PathVariable Long id) {
        if (!workerService.workerExists(id)) {
            throw new ResourceNotFoundException("Worker with id " + id + " not found");
        }
        workerService.deleteWorker(id);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}