package com.api.skillShare.service.Impl;

import com.api.skillShare.dto.ReviewRequestDto;
import com.api.skillShare.exception.ResourceNotFoundException;
import com.api.skillShare.model.Review;
import com.api.skillShare.repository.ReviewRepository;
import com.api.skillShare.service.ReviewService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequestScope
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Review addReview(ReviewRequestDto reviewRequestDto) {
        Review review = Review.builder()
                .rating(reviewRequestDto.getRating())
                .feedback(reviewRequestDto.getFeedback().orElse(null))
                .build();
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsBySkillId(Long skillId) {
        Optional<List<Review>> listOfReviews = reviewRepository.findBySkillRequest_Skill_Id(skillId);
        if (listOfReviews.isPresent()) {
            return listOfReviews.get();
        } else {
            throw new ResourceNotFoundException("Request: " + skillId + " still doesn't exist");
        }
    }

    @Override
    public Review updateReview(String reviewId, ReviewRequestDto reviewRequestDto) {
        UUID id = UUID.fromString(reviewId);
        Optional<Review> review = reviewRepository.findById(id);

        if (review.isPresent()) {
            Review updatedReview = review.get();
            updatedReview.setRating(reviewRequestDto.getRating());
            updatedReview.setFeedback(reviewRequestDto.getFeedback().orElse(null));

            return reviewRepository.save(updatedReview);
        } else {
            throw new ResourceNotFoundException("Review: " + reviewId + " doesn't exist");
        }
    }

    @Override
    public void deleteReview(String reviewId) {
        UUID id = UUID.fromString(reviewId);
        if (!reviewRepository.existsById(id)) {
            throw new ResourceNotFoundException("Review: " + reviewId + " doesn't exist");
        }

        reviewRepository.deleteById(id);
    }
}
