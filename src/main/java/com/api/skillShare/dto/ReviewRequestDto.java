package com.api.skillShare.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@NoArgsConstructor
public class ReviewRequestDto {
    @NotNull
    @Min(value = 1, message = "Rating should not be less than 1")
    @Max(value = 5, message = "Rating should not be greater than 5")
    @Setter
    private int rating;

    private Optional<@Size(max = 500, message = "Feedback should not be greater than 500 characters long") String> feedback;

    public ReviewRequestDto(int rating, String feedback) {
        this.rating = rating;
        this.feedback = Optional.ofNullable(feedback);
    }

    public void setFeedback(String feedback) {
        this.feedback = Optional.ofNullable(feedback);
    }
}
