package com.jobportal.jobservice.dto;

import com.jobportal.jobservice.domain.SkillCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record JobSkillRequest(
        @NotBlank(message="skill name is required")
        @Size(max=100,message="name must not exceed 100 characters")
        String name,

        @NotNull(message="skill category is required")
        SkillCategory category
) {
}
