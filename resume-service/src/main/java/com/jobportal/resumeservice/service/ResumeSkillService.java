package com.jobportal.resumeservice.service;

import com.jobportal.resumeservice.dto.ResumeSkillRequest;
import com.jobportal.resumeservice.dto.ResumeSkillResponse;
import java.util.List;

public interface ResumeSkillService {
    ResumeSkillResponse addSkill(Long resumeId, Long candidateId, ResumeSkillRequest request);
    List<ResumeSkillResponse> getSkills(Long resumeId);
    ResumeSkillResponse updateSkill(Long skillId, Long resumeId, Long candidateId, ResumeSkillRequest request);
    void deleteSkill(Long resumeId, Long skillId, Long candidateId);
}
