package com.jobportal.resumeservice.util;

import com.jobportal.resumeservice.domain.WorkExperience;
import com.jobportal.resumeservice.dto.WorkExperienceResponse;

public class WorkExperienceMapper {

    public static WorkExperienceResponse  toDto(WorkExperience workExperience) {
        return WorkExperienceResponse.builder()
                .id(workExperience.getId())
                .companyName(workExperience.getCompanyName())
                .companyLogoUrl(workExperience.getCompanyLogoUrl())
                .jobTitle(workExperience.getJobTitle())
                .employmentType(workExperience.getEmploymentType())
                .location(workExperience.getLocation())
                .startDate(workExperience.getStartDate())
                .endDate(workExperience.getEndDate())
                .isCurrentJob(workExperience.getIsCurrentJob())
                .description(workExperience.getDescription())
                .technologies(workExperience.getTechnologies())
                .displayOrder(workExperience.getDisplayOrder())
                .createdAt(workExperience.getCreatedAt())
                .updatedAt(workExperience.getUpdatedAt())
                .resume(workExperience.getResume())
                .build();
    }
}
