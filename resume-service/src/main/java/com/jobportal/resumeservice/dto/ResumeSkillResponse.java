package com.jobportal.resumeservice.dto;

import com.jobportal.resumeservice.domain.ProficiencyLevel;
import lombok.Builder;

@Builder
public record ResumeSkillResponse(
        Long id,
        String skillName,
        ProficiencyLevel proficiencyLevel,
        Integer yearsOfExperience,
        Integer displayOrder
) {
}
