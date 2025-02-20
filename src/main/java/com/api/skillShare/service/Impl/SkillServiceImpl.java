package com.api.skillShare.service.Impl;

import com.api.skillShare.dto.SkillRequestDto;
import com.api.skillShare.exception.ResourceNotFoundException;
import com.api.skillShare.model.Skill;
import com.api.skillShare.model.User;
import com.api.skillShare.repository.SkillRepository;
import com.api.skillShare.repository.UserRepository;
import com.api.skillShare.service.SkillService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequestScope
@RequiredArgsConstructor
@Transactional
public class SkillServiceImpl implements SkillService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Override
    public Skill createSkill(SkillRequestDto skillRequestDto) {
        User user = userRepository
                .findById(UUID.fromString(skillRequestDto.getUserId()))
                .orElseThrow(() -> new ResourceNotFoundException("User: " + skillRequestDto.getUserId() + " not found"));

        Skill skill = Skill.builder()
                .name(skillRequestDto.getName())
                .description(skillRequestDto.getDescription().orElse(null))
                .expertiseLevel(skillRequestDto.getExpertiseLevel())
                .user(user)
                .build();
        return skillRepository.save(skill);
    }

    @Override
    public Skill updateSkill(Long skillId, SkillRequestDto skillRequestDto) {
        Optional<Skill> skill = skillRepository.findById(skillId);

        if (skill.isPresent()) {
            Skill updatedSkill = skill.get();
            if (skillRequestDto.getName() != null) updatedSkill.setName(skillRequestDto.getName());
            if (skillRequestDto.getDescription().isPresent()) updatedSkill.setDescription(skillRequestDto.getDescription().get());
            if(skillRequestDto.getExpertiseLevel() != null) updatedSkill.setExpertiseLevel(skillRequestDto.getExpertiseLevel());

            return skillRepository.save(updatedSkill);
        } else {
            throw new ResourceNotFoundException("Requested skill: " + skillId + " don't exists");
        }
    }

    @Override
    public List<Skill> searchSkills(String keyword) {
        List<Skill> skills = skillRepository.findAll();
        if(keyword == null || keyword.isEmpty()) {
            return skills;
        }

        return skills.stream()
                .filter(skill -> skill.getDescription().contains(keyword) || skill.getName().contains(keyword))
                .toList();
    }

    @Override
    public Skill getSkillById(Long skillId) {
        Optional<Skill> skill = skillRepository.findById(skillId);
        if (skill.isPresent()) {
            return skill.get();
        } else {
            throw new ResourceNotFoundException("Skill: " + skillId + " don't exists");
        }
    }

    @Override
    public void deleteSkill(Long skillId) {
        if (!skillRepository.existsById(skillId)) {
            throw new ResourceNotFoundException("Skill: " + skillId + " don't exists");
        }
        skillRepository.deleteById(skillId);
    }
}
