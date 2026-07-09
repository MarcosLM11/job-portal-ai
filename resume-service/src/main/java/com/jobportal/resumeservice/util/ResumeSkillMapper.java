package com.jobportal.resumeservice.util;

import com.jobportal.resumeservice.domain.ResumeSkill;
import com.jobportal.resumeservice.dto.ResumeSkillResponse;

public class ResumeSkillMapper {

    public static ResumeSkillResponse toDto(ResumeSkill resumeSkill) {
        return ResumeSkillResponse.builder()
                .displayOrder(resumeSkill.getDisplayOrder())
                .skillName(resumeSkill.getSkillName())
                .yearsOfExperience(resumeSkill.getYearsOfExperience())
                .skillName(resumeSkill.getSkillName())
                .proficiencyLevel(resumeSkill.getProficiencyLevel())
                .build();
    }

}
