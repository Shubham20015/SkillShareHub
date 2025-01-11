package com.api.skillShare.model;

import com.api.skillShare.enums.Expertise;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name may not be empty")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters long")
    @Column(unique = true)
    private String name;

    @Size(max = 100, message = "Name should be more than 100 characters long")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "expertise_level", nullable = false)
    private Expertise expertiseLevel;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("skill")
    private List<Review> reviews;

    private final LocalDateTime createdAt = LocalDateTime.now();

    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt;

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
