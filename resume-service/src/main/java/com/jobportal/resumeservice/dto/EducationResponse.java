package com.jobportal.resumeservice.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EducationResponse(
        Long id,
        String institutionName,
        String degree,
        String fieldOfStudy,
        String grade,
        LocalDate startDate,
        LocalDate endDate,
        Boolean isCurrentlyStudiying,
        String description,
        Integer displayOrder
) {
}
