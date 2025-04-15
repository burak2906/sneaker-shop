package com.mobylab.springbackend.service.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderDto {
    private Long id;
    private LocalDateTime orderDate;
    private UUID buyerId;
    private List<Long> sneakerIds;

    public Long getId() { return id; }
    public OrderDto setId(Long id) { this.id = id; return this; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public OrderDto setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; return this; }

    public UUID getBuyerId() { return buyerId; }
    public OrderDto setBuyerId(UUID buyerId) { this.buyerId = buyerId; return this; }

    public List<Long> getSneakerIds() { return sneakerIds; }
    public OrderDto setSneakerIds(List<Long> sneakerIds) { this.sneakerIds = sneakerIds; return this; }
}
