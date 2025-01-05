package com.api.skillShare.repository;

import com.api.skillShare.model.SkillRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRequestRepository extends JpaRepository<SkillRequest, Long> {
}
