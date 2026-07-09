package com.jobportal.resumeservice.util;

import com.jobportal.resumeservice.domain.Education;
import com.jobportal.resumeservice.dto.EducationResponse;

public class EducationMapper {

    public static EducationResponse toDto(Education education) {
        return EducationResponse.builder()
                .id(education.getId())
                .institutionName(education.getInstitutionName())
                .degree(education.getDegree())
                .fieldOfStudy(education.getFieldOfStudy())
                .grade(education.getGrade())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .description(education.getDescription())
                .displayOrder(education.getDisplayOrder())
                .isCurrentlyStudiying(education.getIsCurrentlyStudiying())
                .build();
    }

}
