package com.mobylab.springbackend.service.dto;

import java.util.List;
import java.util.UUID;

public class CartDto {
    private List<Long> sneakerIds;

    public List<Long> getSneakerIds() {
        return sneakerIds;
    }

    public void setSneakerIds(List<Long> sneakerIds) {
        this.sneakerIds = sneakerIds;
    }
}
