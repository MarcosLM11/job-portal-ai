package com.jobportal.resumeservice.repository;

import com.jobportal.resumeservice.domain.ResumeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResumeSkillRepository extends JpaRepository<ResumeSkill, Long> {
    List<ResumeSkill> findByResume_IdOrderByDisplayOrderAsc(Long resumeId);
}
