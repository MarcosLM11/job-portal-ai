package com.jobportal.jobservice.repository;

import com.jobportal.jobservice.domain.JobSkill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSkillRepository extends JpaRepository<JobSkill, Long> {
    Page<JobSkill> findByActiveTrue(Pageable pageable);
    Boolean existsByName(String name);
    Boolean existsBySlug(String slug);
}
