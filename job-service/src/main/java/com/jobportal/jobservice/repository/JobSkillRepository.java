package com.jobportal.jobservice.repository;

import com.jobportal.jobservice.domain.JobSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSkillRepository extends JpaRepository<JobSkill, Long> {
    List<JobSkill> findByActiveTrue();
    Boolean existsByName(String name);
    Boolean existsBySlug(String slug);
}
