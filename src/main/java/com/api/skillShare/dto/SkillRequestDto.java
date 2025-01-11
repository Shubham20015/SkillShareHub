package com.api.skillShare.dto;

import com.api.skillShare.constraint.ValidEnum;
import com.api.skillShare.constraint.ValidUUID;
import com.api.skillShare.enums.Expertise;
import com.api.skillShare.service.marker.PostValidationGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@NoArgsConstructor
public class SkillRequestDto {

    @ValidUUID(groups = PostValidationGroup.class, message = "Correct user id is required")
    @Setter
    private String userId;

    @NotBlank(groups = PostValidationGroup.class, message = "Name is required")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters long")
    @Setter
    private String name;

    private Optional<@Size(max = 100, message = "Name should be more than 100 characters long") String> description = Optional.empty();

    @ValidEnum(groups = PostValidationGroup.class, message = "Invalid expertise level. Must be one of: BEGINNER, INTERMEDIATE, ADVANCED, EXPERT")
    @Setter
    private Expertise expertiseLevel;

    public SkillRequestDto(String name,  String description, Expertise expertiseLevel) {
        this.name = name;
        this.description = Optional.ofNullable(description);
        this.expertiseLevel = expertiseLevel;
    }

    public void setDescription(String description) {
        this.description = Optional.ofNullable(description);
    }
}
