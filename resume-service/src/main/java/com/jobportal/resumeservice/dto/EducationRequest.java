package com.jobportal.resumeservice.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record EducationRequest(
        @NotBlank(message="institution name is required")
        String institutionName,

        @NotBlank(message="degree is required")
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
