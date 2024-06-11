package com.example.Education_Java4b.controllers;

import com.example.Education_Java4b.exceptions.ResourceNotFoundException;
import com.example.Education_Java4b.models.Worker;
import com.example.Education_Java4b.services.SupplierService;
import com.example.Education_Java4b.services.WorkerService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class WorkerControllerTest {

    @Mock
    private WorkerService workerService;

    @Mock
    private SupplierService supplierService;

    @InjectMocks
    private WorkerController workerController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(workerController).build();
    }

    @Test
    void getAllWorkersTest() throws Exception {
        when(workerService.getAllWorkers()).thenReturn(Arrays.asList(new Worker(), new Worker()));

        mockMvc.perform(get("/workers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getWorkerByIdTest() throws Exception {
        when(workerService.getWorkerById(1L)).thenReturn(Optional.of(new Worker()));

        mockMvc.perform(get("/workers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void getWorkerByIdNotFoundTest() throws Exception {
        when(workerService.getWorkerById(1L)).thenThrow(new ResourceNotFoundException("Worker with id 1 not found"));

        mockMvc.perform(get("/workers/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createWorkerTest() throws Exception {
        when(supplierService.supplierExists(1L)).thenReturn(true);
        when(workerService.workerExistsByName("Johnislav")).thenReturn(false);
        when(workerService.workerExistsByEmail("johny@example.com")).thenReturn(false);
        doNothing().when(workerService).createWorker(eq(1L), any(Worker.class));

        mockMvc.perform(post("/workers")
                        .param("supplierId", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Johnislav\",\"email\":\"johny@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void createWorkerInvalidNameTest() throws Exception {
        mockMvc.perform(post("/workers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateWorkerTest() throws Exception {
        when(workerService.workerExists(1L)).thenReturn(true);

        mockMvc.perform(put("/workers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Johny\",\"email\":\"johny@example.com\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void updateWorkerNotFoundTest() throws Exception {
        when(workerService.workerExists(1L)).thenReturn(false);

        mockMvc.perform(put("/workers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Johny\",\"email\":\"johny@example.com\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteWorkerTest() throws Exception {
        when(workerService.workerExists(1L)).thenReturn(true);

        mockMvc.perform(delete("/workers/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteWorkerNotFoundTest() throws Exception {
        when(workerService.workerExists(1L)).thenReturn(false);

        mockMvc.perform(delete("/workers/1"))
                .andExpect(status().isNotFound());
    }
}