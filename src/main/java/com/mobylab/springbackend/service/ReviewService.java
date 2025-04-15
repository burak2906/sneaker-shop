package com.mobylab.springbackend.service;

import com.mobylab.springbackend.entity.Review;
import com.mobylab.springbackend.entity.Sneaker;
import com.mobylab.springbackend.entity.User;
import com.mobylab.springbackend.exception.ResourceNotFoundException;
import com.mobylab.springbackend.repository.ReviewRepository;
import com.mobylab.springbackend.repository.SneakerRepository;
import com.mobylab.springbackend.repository.UserRepository;
import com.mobylab.springbackend.service.dto.ReviewDto;
import com.mobylab.springbackend.service.dto.ReviewRequestDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final SneakerRepository sneakerRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, SneakerRepository sneakerRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.sneakerRepository = sneakerRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userRepository.findUserByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }
        throw new RuntimeException("User not authenticated");
    }

    public ReviewDto addReview(ReviewRequestDto dto) {
        Review review = new Review();
        review.setComment(dto.getComment());
        review.setRating(dto.getRating());

        User user = getCurrentUser();
        review.setReviewer(user);

        Sneaker sneaker = sneakerRepository.findById(dto.getSneakerId())
                .orElseThrow(() -> new RuntimeException("Sneaker not found"));
        review.setSneaker(sneaker);

        return mapToDto(reviewRepository.save(review));
    }

    public List<ReviewDto> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new ResourceNotFoundException("Review not found with id: " + id);
        }
        reviewRepository.deleteById(id);
    }

    public List<ReviewDto> getReviewsBySneakerId(Long sneakerId) {
        List<ReviewDto> reviews = reviewRepository.findAll().stream()
                .filter(r -> r.getSneaker().getId().equals(sneakerId))
                .map(this::mapToDto)
                .collect(Collectors.toList());

        if (reviews.isEmpty()) {
            throw new ResourceNotFoundException("No reviews found for sneaker with id: " + sneakerId);
        }

        return reviews;
    }
    public List<ReviewDto> getReviewsByUserId(UUID userId) {
        List<ReviewDto> reviews = reviewRepository.findAllByReviewerId(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        if (reviews.isEmpty()) {
            throw new ResourceNotFoundException("No reviews found for user with id: " + userId);
        }

        return reviews;
    }

    private ReviewDto mapToDto(Review r) {
        return new ReviewDto()
                .setId(r.getId())
                .setComment(r.getComment())
                .setRating(r.getRating())
                .setReviewerId(r.getReviewer().getId())
                .setSneakerId(r.getSneaker().getId());
    }
}
