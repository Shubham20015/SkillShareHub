package com.api.skillShare.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    @JsonBackReference
    private Skill skill;

    @ManyToOne
    @JoinColumn(name = "reviewer_id", nullable = false)
    @JsonIgnoreProperties({"skills", "requestedSkillRequests", "providedSkillRequests", "bio"})
    private User reviewer;

    @Min(value = 1, message = "Rating should not be less than 1")
    @Max(value = 5, message = "Rating should not be greater than 5")
    private Integer rating;

    @Size(max = 500, message = "Feedback should not be greater than 500 characters long")
    private String feedback;

    private final LocalDateTime createdAt = LocalDateTime.now();

    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt;

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
