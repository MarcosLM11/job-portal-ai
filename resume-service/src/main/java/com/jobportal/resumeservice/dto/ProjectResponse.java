package com.jobportal.resumeservice.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ProjectResponse(
        Long id,
        String title,
        String description,
        List<String> technologies,
        String projectUrl,
        String sourceCodeUrl,
        String startDate,
        String endDate,
        Boolean isOngoing,
        Integer displayOrder
) {
}
