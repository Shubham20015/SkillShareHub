package com.api.skillShare.controller;

import com.api.skillShare.constraint.ValidUUID;
import com.api.skillShare.dto.ReviewRequestDto;
import com.api.skillShare.model.Review;
import com.api.skillShare.service.ReviewService;
import com.api.skillShare.service.marker.PostValidationGroup;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/reviews", produces = APPLICATION_JSON_VALUE)
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody @Validated(PostValidationGroup.class) @NotNull ReviewRequestDto reviewRequestDto) {
        Review review = reviewService.addReview(reviewRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    @GetMapping("/skill/{skillId}")
    public ResponseEntity<List<Review>> getReviewsBySkillId(@PathVariable @NotNull final Long skillId) {
        List<Review> reviews = reviewService.getReviewsBySkillId(skillId);
        return ResponseEntity.ok(reviews);
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable @ValidUUID final String reviewId,
                                             @RequestBody @Validated @NotNull ReviewRequestDto reviewRequestDto) {
        Review review = reviewService.updateReview(reviewId, reviewRequestDto);
        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable @ValidUUID final String reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("Review: " + reviewId + " deleted successfully");
    }
}
