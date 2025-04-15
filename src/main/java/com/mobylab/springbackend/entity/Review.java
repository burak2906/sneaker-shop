package com.mobylab.springbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews", schema = "project")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;
    private int rating;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    @ManyToOne
    @JoinColumn(name = "sneaker_id")
    private Sneaker sneaker;

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public Review setId(Long id) {
        this.id = id;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Review setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public int getRating() {
        return rating;
    }

    public Review setRating(int rating) {
        this.rating = rating;
        return this;
    }

    public User getReviewer() {
        return reviewer;
    }

    public Review setReviewer(User reviewer) {
        this.reviewer = reviewer;
        return this;
    }

    public Sneaker getSneaker() {
        return sneaker;
    }

    public Review setSneaker(Sneaker sneaker) {
        this.sneaker = sneaker;
        return this;
    }
}
