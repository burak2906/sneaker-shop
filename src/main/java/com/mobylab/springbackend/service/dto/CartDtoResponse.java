package com.mobylab.springbackend.service.dto;

import java.util.List;
import java.util.UUID;

public class CartDtoResponse {
    private Long cartId;
    private UUID userId;
    private List<SneakerDto> sneakers;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<SneakerDto> getSneakers() {
        return sneakers;
    }

    public void setSneakers(List<SneakerDto> sneakers) {
        this.sneakers = sneakers;
    }
}
