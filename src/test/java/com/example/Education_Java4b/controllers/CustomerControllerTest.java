package com.example.Education_Java4b.controllers;

import com.example.Education_Java4b.exceptions.ResourceNotFoundException;
import com.example.Education_Java4b.models.Customer;
import com.example.Education_Java4b.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.Optional;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void getAllCustomersTest() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(new Customer(), new Customer()));

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getCustomerByIdTest() throws Exception {
        when(customerService.getCustomerById(1L)).thenReturn(Optional.of(new Customer()));

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void getCustomerByIdNotFoundTest() throws Exception {
        when(customerService.getCustomerById(1L)).thenThrow(new ResourceNotFoundException("Customer with id 1 not found"));

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    void createCustomerTest() throws Exception {
        when(customerService.customerExistsByName("Johny")).thenReturn(false);
        when(customerService.customerExistsByEmail("johny@example.com")).thenReturn(false);
        doNothing().when(customerService).createCustomer(any(Customer.class));

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Johny\", \"email\":\"johny@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void createCustomerInvalidEmailTest() throws Exception {
        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Johny\", \"email\":\"johny\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createCustomerInvalidNameTest() throws Exception {
        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\", \"email\":\"john@example.com\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateCustomerTest() throws Exception {
        when(customerService.customerExists(1L)).thenReturn(true);

        mockMvc.perform(put("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Johny\", \"email\":\"johny@example.com\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void updateCustomerNotFoundTest() throws Exception {
        when(customerService.customerExists(1L)).thenReturn(false);

        mockMvc.perform(put("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\", \"email\":\"john@example.com\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCustomerTest() throws Exception {
        when(customerService.customerExists(1L)).thenReturn(true);

        mockMvc.perform(delete("/customers/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCustomerNotFoundTest() throws Exception {
        when(customerService.customerExists(1L)).thenReturn(false);

        mockMvc.perform(delete("/customers/1"))
                .andExpect(status().isNotFound());
    }
}