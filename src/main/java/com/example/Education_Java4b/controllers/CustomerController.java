package com.example.Education_Java4b.controllers;

import jakarta.validation.Valid;
import com.example.Education_Java4b.dtos.CustomerDTO;
import com.example.Education_Java4b.exceptions.ResourceNotFoundException;
import com.example.Education_Java4b.models.Customer;
import com.example.Education_Java4b.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers().stream()
                .map(CustomerDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id)
                .map(CustomerDTO::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with " + id + " not found"));
    }

    @PostMapping
    public CustomerDTO createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        if (customerService.customerExistsByName(customerDTO.getName())) {
            throw new IllegalArgumentException("A customer with this name already exists");
        }
        if (customerService.customerExistsByEmail(customerDTO.getEmail())) {
            throw new IllegalArgumentException("A customer with this email already exists");
        }
        Customer customer = customerDTO.toEntity();
        customerService.createCustomer(customer);
        return CustomerDTO.fromEntity(customer);
    }

    @PutMapping("/{id}")
    public void updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        if (!customerService.customerExists(id)) {
            throw new ResourceNotFoundException("Customer with id " + id + " not found");
        }
        Customer customer = customerDTO.toEntity();
        customer.setId(id);
        customerService.updateCustomer(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        if (!customerService.customerExists(id)) {
            throw new ResourceNotFoundException("Customer with id " + id + " not found");
        }
        customerService.deleteCustomer(id);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
