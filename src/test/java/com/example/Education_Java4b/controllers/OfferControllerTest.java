package com.example.Education_Java4b.controllers;

import com.example.Education_Java4b.dtos.OfferDTO;
import com.example.Education_Java4b.models.Offer;
import com.example.Education_Java4b.models.OfferStatus;
import com.example.Education_Java4b.services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class OfferControllerTest {

    @Mock
    private OfferService offerService;

    @Mock
    private WorkerService workerService;

    @Mock
    private CustomerService customerService;

    @Mock
    private SupplierService supplierService;

    @InjectMocks
    private OfferController offerController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(offerController).build();
    }

    @Test
    public void testGetAllOffers() throws Exception {
        // Arrange
        Offer offer1 = new Offer();
        offer1.setId(1L);
        Offer offer2 = new Offer();
        offer2.setId(2L);
        when(offerService.getAllOffers()).thenReturn(Arrays.asList(offer1, offer2));

        // Act & Assert
        mockMvc.perform(get("/offers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));

        // Arrange
        when(offerService.getAllOffers()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/offers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetOfferById_ExistingOffer() throws Exception {
        // Arrange
        Offer offer = new Offer();
        offer.setId(1L);
        when(offerService.getOfferById(1L)).thenReturn(Optional.of(offer));

        // Act & Assert
        mockMvc.perform(get("/offers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void testGetOfferById_NonExistingOffer() throws Exception {
        // Arrange
        when(offerService.getOfferById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/offers/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateOffer() throws Exception {
        // Arrange
        Long supplierId = 1L;
        Long customerId = 2L;
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setId(1L);
        offerDTO.setDescription("Something");
        when(supplierService.supplierExists(supplierId)).thenReturn(true);
        when(customerService.customerExists(customerId)).thenReturn(true);
        doNothing().when(offerService).createOffer(anyLong(), anyLong(), any(Offer.class));

        // Act & Assert
        mockMvc.perform(post("/offers")
                        .param("supplierId", supplierId.toString())
                        .param("customerId", customerId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(offerDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateOffer() throws Exception {
        // Arrange
        Long offerId = 1L;
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setId(offerId);
        when(offerService.offerExists(offerId)).thenReturn(true);
        doNothing().when(offerService).updateOffer(any(Offer.class));

        // Act & Assert
        mockMvc.perform(put("/offers/" + offerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(offerDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteOffer() throws Exception {
        // Arrange
        Long offerId = 1L;
        doNothing().when(offerService).deleteOffer(offerId);

        // Act & Assert
        mockMvc.perform(delete("/offers/" + offerId))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOfferStatus() throws Exception {
        // Arrange
        Long offerId = 1L;
        OfferStatus offerStatus = OfferStatus.NEW;
        when(offerService.getOfferStatus(offerId)).thenReturn(Optional.of(offerStatus));

        // Act & Assert
        mockMvc.perform(get("/offers/" + offerId + "/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(offerStatus.toString())));
    }

    @Test
    public void testUpdateOfferStatus() throws Exception {
        // Arrange
        Long offerId = 1L;
        OfferStatus offerStatus = OfferStatus.ACCEPTED;
        when(offerService.offerExists(offerId)).thenReturn(true);
        doNothing().when(offerService).updateOfferStatus(anyLong(), any(OfferStatus.class));

        // Act & Assert
        mockMvc.perform(put("/offers/" + offerId + "/status")
                        .param("status", offerStatus.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddWorkerToOffer() throws Exception {
        // Arrange
        Long offerId = 1L;
        Long workerId = 2L;
        when(offerService.offerExists(offerId)).thenReturn(true);
        when(workerService.workerExists(workerId)).thenReturn(true);
        doNothing().when(offerService).addWorkerToOffer(offerId, workerId);

        // Act & Assert
        mockMvc.perform(post("/offers/" + offerId + "/workers/" + workerId))
                .andExpect(status().isOk());
    }

    @Test
    public void testRemoveWorkerFromOffer() throws Exception {
        // Arrange
        Long offerId = 1L;
        Long workerId = 2L;
        when(offerService.offerExists(offerId)).thenReturn(true);
        when(workerService.workerExists(workerId)).thenReturn(true);
        doNothing().when(offerService).removeWorkerFromOffer(offerId, workerId);

        // Act & Assert
        mockMvc.perform(delete("/offers/" + offerId + "/workers/" + workerId))
                .andExpect(status().isOk());
    }
}