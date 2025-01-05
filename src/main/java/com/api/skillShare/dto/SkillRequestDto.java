package com.api.skillShare.dto;

import com.api.skillShare.constraint.ValidEnum;
import com.api.skillShare.enums.Expertise;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@NoArgsConstructor
public class SkillRequestDto {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 40, message = "Name must be between 2 and 32 characters long")
    @Setter
    private String name;

    private Optional<@Size(max = 100, message = "Name should be more than 100 characters long") String> description;

    @ValidEnum(message = "Invalid expertise level. Must be one of: BEGINNER, INTERMEDIATE, ADVANCED, EXPERT")
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
