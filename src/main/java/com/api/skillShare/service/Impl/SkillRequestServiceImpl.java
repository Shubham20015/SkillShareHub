package com.api.skillShare.service.Impl;

import com.api.skillShare.dto.SkillShareCreateRequestDto;
import com.api.skillShare.dto.SkillShareUpdateRequestDto;
import com.api.skillShare.enums.RequestStatus;
import com.api.skillShare.exception.ResourceNotFoundException;
import com.api.skillShare.model.Skill;
import com.api.skillShare.model.SkillRequest;
import com.api.skillShare.model.User;
import com.api.skillShare.repository.SkillRepository;
import com.api.skillShare.repository.SkillRequestRepository;
import com.api.skillShare.repository.UserRepository;
import com.api.skillShare.service.SkillRequestService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Optional;
import java.util.UUID;

@Service
@RequestScope
@RequiredArgsConstructor
@Transactional
public class SkillRequestServiceImpl implements SkillRequestService {

    @Autowired
    private SkillRequestRepository skillRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Override
    public SkillRequest createSkillRequest(SkillShareCreateRequestDto skillShareCreateRequestDto) {
        Optional<User> requester = userRepository.findById(UUID.fromString(skillShareCreateRequestDto.getRequesterId()));
        Optional<User> provider = userRepository.findById(UUID.fromString(skillShareCreateRequestDto.getProviderId()));
        Optional<Skill> skill = skillRepository.findById(skillShareCreateRequestDto.getSkillId());

        if (requester.isEmpty()) {
            System.out.println();
            throw new ResourceNotFoundException("Requesting user: " + skillShareCreateRequestDto.getRequesterId() + " doesn't exist");
        }
        else if (provider.isEmpty()) {
            throw new ResourceNotFoundException("Provider user: " + skillShareCreateRequestDto.getProviderId() + " doesn't exist");
        }
        else if (skill.isEmpty()) {
            throw new ResourceNotFoundException("Mentioned skill" + skillShareCreateRequestDto.getSkillId() + " doesn't exist");
        }

        SkillRequest skillRequest = SkillRequest.builder()
                .requester(requester.get())
                .provider(provider.get())
                .skill(skill.get())
                .status(RequestStatus.PENDING)
                .build();

        return skillRequestRepository.save(skillRequest);
    }

    @Override
    public SkillRequest updateSkillRequestStatus(Long skillRequestId, SkillShareUpdateRequestDto skillShareUpdateRequestDto) {
        Optional<SkillRequest> skillRequest = skillRequestRepository.findById(skillRequestId);

        if (skillRequest.isPresent()) {
            SkillRequest updatedSkillRequest = skillRequest.get();
            updatedSkillRequest.setStatus(updatedSkillRequest.getStatus());

            return skillRequestRepository.save(updatedSkillRequest);
        } else {
            throw new ResourceNotFoundException("Skill share request: " + skillRequestId + " doesn't exist");
        }
    }

    @Override
    public void deleteSkillRequest(Long skillRequestId) {
        if (!skillRequestRepository.existsById(skillRequestId)) {
            throw new ResourceNotFoundException("Skill share request: " + skillRequestId + " doesn't exist");
        }
        skillRequestRepository.deleteById(skillRequestId);
    }
}
