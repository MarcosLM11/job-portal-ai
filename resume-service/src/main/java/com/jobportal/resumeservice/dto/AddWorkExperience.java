package com.jobportal.resumeservice.dto;

import com.jobportal.resumeservice.domain.JobType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record AddWorkExperience(
        @NotBlank(message="company name is required")
        String companyName,
        String companyLogoUrl,
        @NotBlank(message="job title is required")
        String jobTitle,
        JobType employmentType,
        String location,
        @NotNull(message="start date is required")
        LocalDateTime startDate,
        LocalDateTime endDate,
        Boolean isCurrentJob,
        String description,
        List<String> technologies,
        Integer displayOrder
) {
}
