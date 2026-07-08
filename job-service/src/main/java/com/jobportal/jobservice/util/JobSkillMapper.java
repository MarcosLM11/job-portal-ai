package com.jobportal.jobservice.util;

import com.jobportal.jobservice.domain.JobSkill;
import com.jobportal.jobservice.dto.JobSkillResponse;

public class JobSkillMapper {

    public static JobSkillResponse toDto(JobSkill jobSkill) {
        return JobSkillResponse.builder()
                .id(jobSkill.getId())
                .name(jobSkill.getName())
                .slug(jobSkill.getSlug())
                .category(jobSkill.getCategory())
                .active(jobSkill.getActive())
                .build();
    }
}
