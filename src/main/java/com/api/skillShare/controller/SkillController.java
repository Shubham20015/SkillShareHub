package com.api.skillShare.controller;

import com.api.skillShare.dto.SkillRequestDto;
import com.api.skillShare.model.Skill;
import com.api.skillShare.service.SkillService;
import com.api.skillShare.service.marker.PostValidationGroup;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/skills", produces = APPLICATION_JSON_VALUE)
public class SkillController {

    @Autowired
    private SkillService skillService;

    @PostMapping
    public ResponseEntity<Skill> createSkill(@RequestBody @Validated(PostValidationGroup.class) @NotNull SkillRequestDto skillRequestDto) {
        Skill skill = skillService.createSkill(skillRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(skill);
    }

    @GetMapping
    public ResponseEntity<List<Skill>> searchSkillsBasedOnKeyword(@RequestParam(name = "keyword", required = false) String keyword) {
        List<Skill> skills = skillService.searchSkills(keyword);
        return ResponseEntity.ok(skills);
    }

    @GetMapping("/{skillId}")
    public ResponseEntity<Skill> getSkillById(@PathVariable @NotNull final Long skillId) {
        Skill skill = skillService.getSkillById(skillId);
        return ResponseEntity.ok(skill);
    }

    @PatchMapping("/{skillId}")
    public ResponseEntity<Skill> updateSkill(@PathVariable @NotNull final Long skillId,
                                           @RequestBody @Validated @NotNull SkillRequestDto skillRequestDto) {
        Skill skill = skillService.updateSkill(skillId, skillRequestDto);
        return ResponseEntity.ok(skill);
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<String> deleteSkill(@PathVariable @NotNull final Long skillId) {
        skillService.deleteSkill(skillId);
        return ResponseEntity.ok("Skill: " + skillId + " deleted successfully");
    }
}
