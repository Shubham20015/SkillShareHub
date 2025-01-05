package com.api.skillShare.dto;

import com.api.skillShare.constraint.ValidEnum;
import com.api.skillShare.enums.RequestStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SkillShareUpdateRequestDto {

    @ValidEnum(message = "Invalid status. Must be one of: PENDING, ACCEPTED, REJECTED, COMPLETED")
    private RequestStatus status;
}
