package com.jobportal.jobservice.dto;

import com.jobportal.jobservice.domain.SkillCategory;
import lombok.Builder;

@Builder
public record JobSkillResponse(
        Long id,
        String name,
        String slug,
        SkillCategory category,
        Boolean active
) {
}
