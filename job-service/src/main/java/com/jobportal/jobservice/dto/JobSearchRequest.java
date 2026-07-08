package com.jobportal.jobservice.dto;

import com.jobportal.jobservice.domain.ExperienceLevel;
import com.jobportal.jobservice.domain.JobStatus;
import com.jobportal.jobservice.domain.JobType;
import com.jobportal.jobservice.domain.WorkMode;
import java.math.BigDecimal;
import java.util.List;

public record JobSearchRequest(
        String keyword,
        Long categoryId,
        List<Long> skillIds,
        List<Long> tagIds,
        Long companyId,
        String location,
        BigDecimal minSalary,
        BigDecimal maxSalary,
        JobType jobType,
        WorkMode workMode,
        ExperienceLevel experienceLevel,
        JobStatus jobStatus,
        Integer minOpenings,
        Integer maxOpenings
) {
}
