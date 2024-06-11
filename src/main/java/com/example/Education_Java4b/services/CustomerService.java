package com.example.Education_Java4b.services;

import com.example.Education_Java4b.models.Customer;
import com.example.Education_Java4b.repos.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> getCustomerByName(String name) {
        return customerRepository.findByName(name);
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public boolean customerExists(Long id) {
        return customerRepository.existsById(id);
    }

    public boolean customerExistsByName(String name) {
        return getCustomerByName(name).isPresent();
    }

    public boolean customerExistsByEmail(String email) {
        return getCustomerByEmail(email).isPresent();
    }
}
