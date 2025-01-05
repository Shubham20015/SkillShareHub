package com.api.skillShare.service;

import com.api.skillShare.dto.SkillRequestDto;
import com.api.skillShare.model.Skill;

import java.util.List;

public interface SkillService {
    Skill createSkill(SkillRequestDto skillRequestDto);
    Skill updateSkill(Long skillId, SkillRequestDto skillRequestDto);
    List<Skill> searchSkills(String keyword);
    Skill getSkillById(Long skillId);
    void deleteSkill(Long skillId);
}
