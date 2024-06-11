package com.example.Education_Java4b.dtos;

public class OfferStatusDTO {
    private String status;

    public OfferStatusDTO() {}

    public OfferStatusDTO(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}