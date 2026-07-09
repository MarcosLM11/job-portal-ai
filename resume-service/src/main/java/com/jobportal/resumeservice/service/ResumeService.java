package com.jobportal.resumeservice.service;

import com.jobportal.resumeservice.domain.PersonalInfo;
import com.jobportal.resumeservice.domain.Resume;
import com.jobportal.resumeservice.dto.PersonalInfoRequest;
import com.jobportal.resumeservice.dto.ResumeRequest;
import com.jobportal.resumeservice.dto.ResumeResponse;
import java.util.List;

public interface ResumeService {
    ResumeResponse createResume(Long candidateId, ResumeRequest request);
    ResumeResponse getResumeById(Long resumeId, Long candidateId);
    List<ResumeResponse> getMyResumes(Long candidateId);
    ResumeResponse updatePersonalInfo(Long resumeId, Long candidateId, PersonalInfoRequest personalInfoRequest);
    ResumeResponse updateSummary(Long resumeId, Long candidateId, String summary);
    ResumeResponse setDefaultResume(Long resumeId, Long candidateId);
    void deleteResume(Long resumeId, Long candidateId);
    Resume getResumeEntityById(Long resumeId, Long candidateId);
}
