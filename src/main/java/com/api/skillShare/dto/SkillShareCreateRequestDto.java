package com.api.skillShare.dto;

import com.api.skillShare.constraint.ValidUUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SkillShareCreateRequestDto {

    @NotBlank(message = "Requester id is required")
    @ValidUUID
    private String requesterId;

    @NotBlank(message = "Provider id is required")
    @ValidUUID
    private String providerId;

    @NotNull(message = "Skill id is required")
    private Long skillId;
}
