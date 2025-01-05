package com.api.skillShare.service;

import com.api.skillShare.dto.SkillShareCreateRequestDto;
import com.api.skillShare.dto.SkillShareUpdateRequestDto;
import com.api.skillShare.model.SkillRequest;

public interface SkillRequestService {
    SkillRequest createSkillRequest(SkillShareCreateRequestDto skillShareCreateRequestDto);
    SkillRequest updateSkillRequestStatus(Long skillRequestId, SkillShareUpdateRequestDto skillShareUpdateRequestDto);
    void deleteSkillRequest(Long skillRequestId);
}
