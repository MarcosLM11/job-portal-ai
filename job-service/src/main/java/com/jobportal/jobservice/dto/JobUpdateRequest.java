package com.jobportal.jobservice.dto;

import com.jobportal.jobservice.domain.ExperienceLevel;
import com.jobportal.jobservice.domain.JobType;
import com.jobportal.jobservice.domain.WorkMode;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record JobUpdateRequest(
        @NotBlank(message="job title is required")
        String title,
        @NotBlank(message="description is required")
        String description,
        String requirements,
        String responsibilities,
        String benefits,
        Long companyId,
        @NotNull(message="category is required")
        Long categoryId,
        Set<Long> skillIds,
        Set<Long> tagIds,
        String address,
        String city,
        String state,
        String country,
        String zipCode,
        @DecimalMin(value="0.0",inclusive=true,message="min salary must not be negative")
        BigDecimal minSalary,
        @DecimalMin(value="0.0",inclusive=true,message="max salary must not be negative")
        BigDecimal maxSalary,
        @NotNull(message="job type is required")
        JobType jobType,
        @NotNull(message="work mode is required")
        WorkMode workMode,
        @NotNull(message="experience level is required")
        ExperienceLevel experienceLevel,
        @Min(value=1,message="openings must be at least 1")
        Integer openings,
        LocalDate applicationDeadLine,
        LocalDate expiresAt
) {
}
