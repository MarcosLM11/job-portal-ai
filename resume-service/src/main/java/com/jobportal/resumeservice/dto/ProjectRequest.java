package com.jobportal.resumeservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

public record ProjectRequest(
        @NotBlank(message = "Project title is required")
        String title,
        List<String> technologies,
        @Pattern(regexp = "^(https?://)?(www\\.)?([\\w]+\\.)+\\w{2,63}/?$", message = "Invalid project URL")
        String projectUrl,
        @Pattern(regexp = "^(https?://)?(www\\.)?([\\w]+\\.)+\\w{2,63}/?$", message = "Invalid source code URL")
        String sourceCodeUrl,
        LocalDate startDate,
        LocalDate endDate,
        Boolean isOngoing,
        Integer displayOrder
) {
}
