package com.jobportal.resumeservice.dto;

import com.jobportal.resumeservice.domain.PersonalInfo;
import com.jobportal.resumeservice.domain.ResumeTemplate;
import com.jobportal.resumeservice.domain.ResumeVisibility;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record ResumeResponse(
        Long id,
        Long candidateId,
        String title,
        ResumeTemplate template,
        ResumeVisibility visibility,
        Boolean isDefault,
        PersonalInfoResponse personalInfo,
        String summary,
        Boolean isActive,
        Integer completionScore,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

        //private List<WorkExperienceResponse> workExperiences;
        //private List<EducationResponse> educations;
        //private List<ResumeSkillResponse> skills;
        //private List<ProjectResponse> projects;
        //private List<CertificationResponse> certifications;
        //private List<AwardResponse> awards;
        //private List<LanguageResponse> languages;
) {
}
