package com.api.skillShare.service.Impl;

import com.api.skillShare.dto.ReviewRequestDto;
import com.api.skillShare.exception.ResourceNotFoundException;
import com.api.skillShare.model.Review;
import com.api.skillShare.model.Skill;
import com.api.skillShare.model.User;
import com.api.skillShare.repository.ReviewRepository;
import com.api.skillShare.repository.SkillRepository;
import com.api.skillShare.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Override
    public Review addReview(ReviewRequestDto reviewRequestDto) {
        User reviewer = userRepository
                .findById(UUID.fromString(reviewRequestDto.getReviewerId()))
                .orElseThrow(() -> new ResourceNotFoundException("User: " + reviewRequestDto.getReviewerId() + " not found"));

        Skill skill = skillRepository
                .findById(reviewRequestDto.getSkillId())
                .orElseThrow(() -> new ResourceNotFoundException("Skill id: " + reviewRequestDto.getSkillId() + " not found"));

        Review review = Review.builder()
                .reviewer(reviewer)
                .skill(skill)
                .rating(reviewRequestDto.getRating())
                .feedback(reviewRequestDto.getFeedback().orElse(null))
                .build();
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsBySkillId(Long skillId) {
        Optional<Skill> skill = skillRepository.findById(skillId);
        if (skill.isPresent() && !skill.get().getReviews().isEmpty()) {
            return skill.get().getReviews();
        } else {
            throw new ResourceNotFoundException("Requested skill id: " + skillId + " still doesn't exist");
        }
    }

    @Override
    public Review updateReview(String reviewId, ReviewRequestDto reviewRequestDto) {
        UUID id = UUID.fromString(reviewId);
        Optional<Review> review = reviewRepository.findById(id);

        if (review.isPresent()) {
            Review updatedReview = review.get();
            if(reviewRequestDto.getRating() != null) updatedReview.setRating(reviewRequestDto.getRating());
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
