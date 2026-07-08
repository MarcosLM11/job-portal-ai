package com.jobportal.jobservice.dto;

import com.jobportal.jobservice.domain.ExperienceLevel;
import com.jobportal.jobservice.domain.JobStatus;
import com.jobportal.jobservice.domain.JobType;
import com.jobportal.jobservice.domain.WorkMode;
import com.jobportal.jobservice.dto.company.CompanyResponse;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record JobResponse(
        Long id,
        String title,
        String description,
        String requirements,
        String responsibilities,
        String benefits,

        CompanyResponse company,
        Long employerId,

        JobCategoryResponse category,
        Set<JobSkillResponse> skills,
        Set<JobTagResponse> tags,

        String address,
        String city,
        String state,
        String country,
        String zipCode,

        BigDecimal minSalary,
        BigDecimal maxSalary,

        JobType jobType,
        WorkMode workMode,
        ExperienceLevel experienceLevel,
        JobStatus jobStatus,

        Integer openings,
        LocalDate applicationDeadLine,
        LocalDate expiresAt,
        Boolean active,

        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime publishedAt,
        LocalDateTime closedAt
) {
}
