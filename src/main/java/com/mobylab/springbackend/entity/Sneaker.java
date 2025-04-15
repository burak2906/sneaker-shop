package com.mobylab.springbackend.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sneakers", schema = "project")
public class Sneaker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String size;
    private double price;

    @ManyToMany(mappedBy = "sneakers")
    private List<Cart> carts;

    @OneToMany(mappedBy = "sneaker")
    private List<Review> reviews;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }


    public List<Cart> getCarts() { return carts; }
    public void setCarts(List<Cart> carts) { this.carts = carts; }

    public List<Review> getReviews() {
        return reviews;
    }

    public Sneaker setReviews(List<Review> reviews) {
        this.reviews = reviews;
        return this;
    }

}
