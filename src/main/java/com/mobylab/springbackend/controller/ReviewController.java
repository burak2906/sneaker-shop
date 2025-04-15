package com.mobylab.springbackend.controller;

import com.mobylab.springbackend.service.ReviewService;
import com.mobylab.springbackend.service.dto.ReviewDto;
import com.mobylab.springbackend.service.dto.ReviewRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController implements SecuredRestController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ReviewDto> addReview(@RequestBody ReviewRequestDto dto) {
        return ResponseEntity.ok(reviewService.addReview(dto));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<List<ReviewDto>> getAll() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<List<ReviewDto>> getReviewsByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId));
    }


    @GetMapping("/sneaker/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<List<ReviewDto>> getReviewsForSneaker(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewsBySneakerId(id));
    }

}
