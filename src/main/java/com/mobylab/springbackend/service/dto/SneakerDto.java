package com.mobylab.springbackend.service.dto;

import java.util.UUID;

public class SneakerDto {
    private Long id;
    private String name;
    private String size;
    private double price;


    public Long getId() { return id; }
    public SneakerDto setId(Long id) { this.id = id; return this; }

    public String getName() { return name; }
    public SneakerDto setName(String name) { this.name = name; return this; }

    public String getSize() { return size; }
    public SneakerDto setSize(String size) { this.size = size; return this; }

    public double getPrice() { return price; }
    public SneakerDto setPrice(double price) { this.price = price; return this; }
}
