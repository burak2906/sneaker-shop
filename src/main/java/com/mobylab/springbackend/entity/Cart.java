package com.mobylab.springbackend.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "carts", schema = "project")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "cart_sneakers",
            schema = "project",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "sneaker_id")
    )
    private List<Sneaker> sneakers;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<Sneaker> getSneakers() { return sneakers; }
    public void setSneakers(List<Sneaker> sneakers) { this.sneakers = sneakers; }
}
