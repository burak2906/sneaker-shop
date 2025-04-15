package com.mobylab.springbackend.service.dto;

import java.util.UUID;

public class ReviewDto {
    private Long id;
    private String comment;
    private int rating;
    private Long sneakerId;
    private UUID reviewerId;

    public Long getId() { return id; }
    public ReviewDto setId(Long id) { this.id = id; return this; }

    public String getComment() { return comment; }
    public ReviewDto setComment(String comment) { this.comment = comment; return this; }

    public int getRating() { return rating; }
    public ReviewDto setRating(int rating) { this.rating = rating; return this; }

    public Long getSneakerId() { return sneakerId; }
    public ReviewDto setSneakerId(Long sneakerId) { this.sneakerId = sneakerId; return this; }

    public UUID getReviewerId() { return reviewerId; }
    public ReviewDto setReviewerId(UUID reviewerId) { this.reviewerId = reviewerId; return this; }
}
