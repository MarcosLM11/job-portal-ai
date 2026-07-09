package com.jobportal.resumeservice.service;

import com.jobportal.resumeservice.domain.WorkExperience;
import com.jobportal.resumeservice.dto.AddWorkExperience;
import com.jobportal.resumeservice.dto.WorkExperienceResponse;
import java.util.List;

public interface WorkExperienceService {
    WorkExperienceResponse addWorkExperience(Long resumeId, Long candidateId, AddWorkExperience request);
    List<WorkExperienceResponse> getWorkExperiences(Long resumeId);
    WorkExperienceResponse updateWorkExperience(Long resumeId, Long candidateId, Long workExperienceId, AddWorkExperience request);
    void deleteWorkExperience(Long resumeId, Long workExperienceId, Long candidateId);
    WorkExperience getWorkExperienceEntityById(Long workExperienceId);
}
