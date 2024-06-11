package com.example.Education_Java4b.controllers;

import com.example.Education_Java4b.exceptions.ResourceNotFoundException;
import com.example.Education_Java4b.models.Supplier;
import com.example.Education_Java4b.services.SupplierService;
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
class SupplierControllerTest {

    @Mock
    private SupplierService supplierService;

    @InjectMocks
    private SupplierController supplierController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(supplierController).build();
    }

    @Test
    void getAllSuppliersTest() throws Exception {
        when(supplierService.getAllSuppliers()).thenReturn(Arrays.asList(new Supplier(), new Supplier()));

        mockMvc.perform(get("/suppliers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getSupplierByIdTest() throws Exception {
        when(supplierService.getSupplierById(1L)).thenReturn(Optional.of(new Supplier()));

        mockMvc.perform(get("/suppliers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void getSupplierByIdNotFoundTest() throws Exception {
        when(supplierService.getSupplierById(1L)).thenThrow(new ResourceNotFoundException("Supplier with id 1 not found"));

        mockMvc.perform(get("/suppliers/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createSupplierTest() throws Exception {
        when(supplierService.supplierExistsByName("Johny")).thenReturn(false);
        doNothing().when(supplierService).createSupplier(any(Supplier.class));

        mockMvc.perform(post("/suppliers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Johny\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void updateSupplierTest() throws Exception {
        when(supplierService.supplierExists(1L)).thenReturn(true);

        mockMvc.perform(put("/suppliers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void updateSupplierNotFoundTest() throws Exception {
        when(supplierService.supplierExists(1L)).thenReturn(false);

        mockMvc.perform(put("/suppliers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteSupplierTest() throws Exception {
        when(supplierService.supplierExists(1L)).thenReturn(true);

        mockMvc.perform(delete("/suppliers/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteSupplierNotFoundTest() throws Exception {
        when(supplierService.supplierExists(1L)).thenReturn(false);

        mockMvc.perform(delete("/suppliers/1"))
                .andExpect(status().isNotFound());
    }
}