package com.jobportal.resumeservice.dto;

import com.jobportal.resumeservice.domain.JobType;
import com.jobportal.resumeservice.domain.Resume;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record WorkExperienceResponse(
        Long id,
        Resume resume,
        String companyName,
        String companyLogoUrl,
        String jobTitle,
        JobType employmentType,
        String location,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Boolean isCurrentJob,
        String description,
        List<String> technologies,
        Integer displayOrder,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
