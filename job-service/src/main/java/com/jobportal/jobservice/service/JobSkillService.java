package com.jobportal.jobservice.service;

import com.jobportal.jobservice.dto.JobSkillRequest;
import com.jobportal.jobservice.dto.JobSkillResponse;
import java.util.List;
import java.util.Set;

public interface JobSkillService {
    JobSkillResponse createSkill(JobSkillRequest request);
    List<JobSkillResponse> getAllSkills();
    JobSkillResponse getSkillById(Long id);
    JobSkillResponse updateSkill(Long id, JobSkillRequest request);
    void deleteSkillById(Long id);
    Set<JobSkillResponse> getSkillsByIds(Set<Long> ids);
}
