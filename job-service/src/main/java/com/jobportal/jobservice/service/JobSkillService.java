package com.jobportal.jobservice.service;

import com.jobportal.jobservice.domain.JobSkill;
import com.jobportal.jobservice.dto.JobSkillRequest;
import com.jobportal.jobservice.dto.JobSkillResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Set;

public interface JobSkillService {
    JobSkillResponse createSkill(JobSkillRequest request);
    Page<JobSkillResponse> getAllSkills(Pageable pageable);
    JobSkillResponse getSkillById(Long id);
    JobSkillResponse updateSkill(Long id, JobSkillRequest request);
    void deleteSkill(Long id);
    Set<JobSkill> getSkillsByIds(Set<Long> ids);
}
