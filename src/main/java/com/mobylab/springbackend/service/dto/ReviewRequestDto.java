package com.mobylab.springbackend.service.dto;

public class ReviewRequestDto {
    private int rating;
    private String comment;
    private Long sneakerId;
    // setters and getters
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Long getSneakerId() {
        return sneakerId;
    }
    public void setSneakerId(Long sneakerId) {
        this.sneakerId = sneakerId;
    }

}
