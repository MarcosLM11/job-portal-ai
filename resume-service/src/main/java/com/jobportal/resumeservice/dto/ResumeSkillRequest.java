package com.jobportal.resumeservice.dto;

import com.jobportal.resumeservice.domain.ProficiencyLevel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ResumeSkillRequest(
        @NotBlank(message = "Skill name is required")
        @Size(max = 100, message = "Skill name must not exceed 100 characters")
        String skillName,
        @NotNull(message = "Proficiency level is required")
        ProficiencyLevel proficiencyLevel,
        @Min(value = 0, message = "Years of experience must be a non-negative integer")
        Integer yearsOfExperience,
        Integer displayOrder) {
}
