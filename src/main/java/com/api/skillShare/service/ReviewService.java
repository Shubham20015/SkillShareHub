package com.api.skillShare.service;

import com.api.skillShare.dto.ReviewRequestDto;
import com.api.skillShare.model.Review;

import java.util.List;

public interface ReviewService {
    Review addReview(ReviewRequestDto reviewRequestDto);
    List<Review> getReviewsBySkillId(Long skillId);
    Review updateReview(String reviewId, ReviewRequestDto reviewRequestDto);
    void deleteReview(String reviewId);
}
