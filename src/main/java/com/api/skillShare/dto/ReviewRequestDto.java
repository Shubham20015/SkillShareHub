package com.api.skillShare.dto;

import com.api.skillShare.constraint.ValidUUID;
import com.api.skillShare.service.marker.PostValidationGroup;
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

    @ValidUUID(groups = PostValidationGroup.class, message = "Correct user id is required")
    @Setter
    private String reviewerId;

    @NotNull(groups = PostValidationGroup.class, message = "Skill id is required")
    @Setter
    private Long skillId;

    @Min(value = 1, message = "Rating should not be less than 1")
    @Max(value = 5, message = "Rating should not be greater than 5")
    @Setter
    private Integer rating;

    private Optional<@Size(max = 500, message = "Feedback should not be greater than 500 characters long") String> feedback = Optional.empty();

    public ReviewRequestDto(int rating, String feedback) {
        this.rating = rating;
        this.feedback = Optional.ofNullable(feedback);
    }

    public void setFeedback(String feedback) {
        this.feedback = Optional.ofNullable(feedback);
    }
}
