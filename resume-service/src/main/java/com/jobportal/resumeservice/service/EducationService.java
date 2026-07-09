package com.jobportal.resumeservice.service;

import com.jobportal.resumeservice.dto.EducationRequest;
import com.jobportal.resumeservice.dto.EducationResponse;
import java.util.List;

public interface EducationService {
    EducationResponse addEducation(Long resumeId, Long candidateId, EducationRequest request);
    List<EducationResponse> getEducations(Long resumeId);
    EducationResponse updateEducation(Long educationId, Long resumeId, Long candidateId, EducationRequest request);
    void deleteEducation(Long educationId, Long resumeId, Long candidateId);

}
