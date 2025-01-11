package com.api.skillShare.controller;

import com.api.skillShare.dto.SkillShareCreateRequestDto;
import com.api.skillShare.dto.SkillShareUpdateRequestDto;
import com.api.skillShare.model.SkillRequest;
import com.api.skillShare.service.SkillRequestService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/skill-request", produces = APPLICATION_JSON_VALUE)
public class SkillRequestController {

    @Autowired
    private SkillRequestService skillRequestService;

    @PostMapping
    public ResponseEntity<SkillRequest> createSkillRequest(@RequestBody @Valid @NotNull SkillShareCreateRequestDto skillShareCreateRequestDto) {
        SkillRequest skillRequest = skillRequestService.createSkillRequest(skillShareCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(skillRequest);
    }

    @PatchMapping("/{skillRequestId}")
    public ResponseEntity<SkillRequest> updateSkillRequestStatus(@PathVariable @NotNull final Long skillRequestId,
                                                                 @RequestBody @Valid @NotNull SkillShareUpdateRequestDto skillShareUpdateRequestDto) {
        SkillRequest skillRequest = skillRequestService.updateSkillRequestStatus(skillRequestId, skillShareUpdateRequestDto);
        return ResponseEntity.ok(skillRequest);
    }

    @DeleteMapping("/{skillRequestId}")
    public ResponseEntity<String> deleteSkillRequest(@PathVariable @NotNull final Long skillRequestId) {
        skillRequestService.deleteSkillRequest(skillRequestId);
        return ResponseEntity.ok("Request: " + skillRequestId + " deleted successfully");
    }
}
